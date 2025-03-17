package com.pdcase.aulas.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdcase.aulas.models.Aula;
import com.pdcase.aulas.models.Grupo;
import com.pdcase.aulas.models.Projeto;
import com.pdcase.aulas.models.Usuario;
import com.pdcase.aulas.repository.AulaRepository;
import com.pdcase.aulas.repository.GrupoRepository;
import com.pdcase.aulas.repository.ProjetoRepository;
import com.pdcase.aulas.repository.UsuarioRepository;

import jakarta.validation.Valid;

@Controller
public class GrupoController {

	@Autowired
	private GrupoRepository gr;

	@Autowired
	private UsuarioRepository ur;

	@Autowired
	private AulaRepository ar;

	@Autowired
	private ProjetoRepository pr;

	// CADASTRAR GRUPO
	@RequestMapping(value = "/cadastrarGrupo", method = RequestMethod.GET)
	public String form() {
		return "grupo/formGrupo";
	}

	@RequestMapping(value = "/cadastrarGrupo", method = RequestMethod.POST)
	public String form(@Valid Grupo grupo, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			attributes.addFlashAttribute("mensagem", "Verifique os campos...");
			return "redirect:/cadastrarGrupo";
		}
		gr.save(grupo);
		attributes.addFlashAttribute("mensagem", "Grupo cadastrado com sucesso!");
		return "redirect:/grupos";
	}

	// LISTAR GRUPOS
	@RequestMapping("/grupos")
	public ModelAndView listaGrupos() {
		ModelAndView mv = new ModelAndView("grupo/listaGrupo");
		Iterable<Grupo> grupos = gr.findAll();
		mv.addObject("grupos", grupos);
		return mv;
	}

	// DETALHES DO GRUPO
	@RequestMapping(value = "/grupo/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesGrupo(@PathVariable("id") long id) {
		Grupo grupo = gr.findById(id);
		ModelAndView mv = new ModelAndView("grupo/detalhesGrupo");
		mv.addObject("grupo", grupo);
		Iterable<Usuario> usuarios = ur.findByGrupo(grupo);
		mv.addObject("usuarios", usuarios);
		List<Projeto> projetos = (List<Projeto>) pr.findAll();
		mv.addObject("projetos", projetos);
		List<Aula> aulas = (List<Aula>) ar.findAll();
	    mv.addObject("aulas", aulas);
		return mv;
	}
	// DELETAR GRUPO

	@RequestMapping("/deletarGrupo")
	public String deletarGrupo(long id) {
		Grupo grupo = gr.findById(id);
		gr.delete(grupo);
		return "redirect:/grupos";
	}

	// Adicionar USUARIO
	@RequestMapping(value = "grupo/{id}", method = RequestMethod.POST)
	public String detalhesGrupoPost(@PathVariable("id") long id, @Valid Usuario usuario, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/grupo/{id}";
		}

		Grupo grupo = gr.findById(id);
		usuario.setGrupo(grupo);
		ur.save(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário adicionado com sucesso!");
		return "redirect:/grupo/{id}";
	}

	// DELETAR USUARIO pelo ID
	@RequestMapping("/deletarUsuario")
	public String deletarUsuario(@RequestParam("id") Long id) {
		Usuario usuario = ur.getById(id);
		Grupo grupo = usuario.getGrupo();
		Long id_grupo = grupo.getId_grupo();
		ur.delete(usuario);
		return "redirect:/grupo/" + id_grupo;
	}

	// Métodos que atualizam grupos
	// formulário edição de grupo
	@RequestMapping(value = "/editar-grupo", method = RequestMethod.GET)
	public ModelAndView editarGrupo(long id) {
		Grupo grupo = gr.findById(id);
		ModelAndView mv = new ModelAndView("grupo/update-grupo");
		mv.addObject("grupo", grupo);
		return mv;
	}

	// UPDATE grupo
	@RequestMapping(value = "/editar-grupo", method = RequestMethod.POST)
	public String updateGrupo(@Valid Grupo grupo, BindingResult result, RedirectAttributes attributes) {
		gr.save(grupo);
		attributes.addFlashAttribute("sucess ", "Grupo alterado com sucesso!");
		long idLong = grupo.getId_grupo();
		String id = "" + idLong;
		return "redirect:/grupos";
	}

	// VINCULAR PROJETO AO GRUPO
	@RequestMapping(value = "grupo/{id}/add-projeto", method = RequestMethod.POST)
	public String adicionarProjetoAoGrupo(@PathVariable("id") long idGrupo, @RequestParam("projetoId") long idProjeto,
			RedirectAttributes attributes) {

		Grupo grupo = gr.findById(idGrupo);
		Projeto projeto = pr.findById(idProjeto);

		if (grupo == null || projeto == null) {
			attributes.addFlashAttribute("mensagemProjeto", "Grupo ou projeto não encontrado!");
			return "redirect:/grupo/" + idGrupo;
		}

		// Adiciona o projeto ao grupo e persiste a relação
		grupo.getProjetos().add(projeto);
		projeto.getGrupos().add(grupo);

		gr.save(grupo); // Salva a atualização no banco

		attributes.addFlashAttribute("mensagemProjeto", "Projeto adicionado com sucesso ao grupo!");
		return "redirect:/grupo/" + idGrupo;
	}

	@GetMapping("favicon.ico")
	@ResponseBody
	public void returnNoFavicon() {

	}

	@PostMapping("/grupo/{id}/remover-projeto")
	public String removerProjetoDoGrupo(@PathVariable("id") Long idGrupo, @RequestParam("projetoId") Long idProjeto) {
		Grupo grupo = gr.findById(idGrupo).orElse(null);
		Projeto projeto = pr.findById(idProjeto).orElse(null);

		if (grupo != null && projeto != null) {
			grupo.getProjetos().remove(projeto);
			gr.save(grupo);
		}

		return "redirect:/grupo/" + idGrupo;
	}

	// VINCULAR AULA AO GRUPO
	@RequestMapping(value = "grupo/{id}/add-aula", method = RequestMethod.POST)
	public String adicionarAulaAoGrupo(@PathVariable("id") long idGrupo, @RequestParam("aulaId") long idAula,
			RedirectAttributes attributes) {

		Grupo grupo = gr.findById(idGrupo);
		Aula aula = ar.getById(idAula);

		if (grupo == null || aula == null) {
			attributes.addFlashAttribute("mensagem", "Grupo ou aula não encontrado!");
			return "redirect:/grupo/" + idGrupo;
		}

		// Adiciona a aula ao grupo e persiste a relação
		grupo.getAulas().add(aula);
		aula.getGrupos().add(grupo);

		gr.save(grupo); // Salva a atualização no banco

		attributes.addFlashAttribute("mensagem", "Aula adicionada com sucesso ao grupo!");
		return "redirect:/grupo/" + idGrupo;
	}

	@PostMapping("/grupo/{id}/remover-aula")
	public String removerAulaDoGrupo(@PathVariable("id") Long idGrupo, @RequestParam("aulaId") Long idAula) {
		Grupo grupo = gr.findById(idGrupo).orElse(null);
		Aula aula = ar.findById(idAula).orElse(null);

		if (grupo != null && aula != null) {
			grupo.getAulas().remove(aula);
			gr.save(grupo);
		}

		return "redirect:/grupo/" + idGrupo;
	}

}