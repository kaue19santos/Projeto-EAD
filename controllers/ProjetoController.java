// VAGA = PROJETO , CANDIDATO = AULA

package com.pdcase.aulas.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pdcase.aulas.models.Aula;
import com.pdcase.aulas.models.Grupo;
import com.pdcase.aulas.models.Projeto;
import com.pdcase.aulas.repository.AulaRepository;
import com.pdcase.aulas.repository.GrupoRepository;
import com.pdcase.aulas.repository.ProjetoRepository;

import jakarta.validation.Valid;

@Controller
public class ProjetoController {

	@Autowired
	private ProjetoRepository pr;
	
	@Autowired
	private AulaRepository ar;
	
	@Autowired
	private GrupoRepository gr;

	// Cadastrar projeto
	@RequestMapping(value = "/cadastrarProjeto", method = RequestMethod.GET) // determina a rota, define a url do método
	public String form() {
		return "projeto/formProjeto";
	}

	@RequestMapping(value = "/cadastrarProjeto", method = RequestMethod.POST)
	public String form(@Valid Projeto projeto, BindingResult result, RedirectAttributes attributes) {
	    if (result.hasErrors()) {
	        System.out.println(result.getAllErrors());
	        attributes.addFlashAttribute("mensagem", "Verifique os campos...");
	        return "redirect:/cadastrarProjeto";
	    }

	    projeto.setData_atualizacao(LocalDateTime.now()); // Define data de atualização

	    pr.save(projeto);
	    attributes.addFlashAttribute("mensagem", "Projeto cadastrado com sucesso!");
	    return "redirect:/projetos";
	}

	// LISTA PROJETOS
	@RequestMapping("/projetos")
	public ModelAndView listaProjetos() {
		ModelAndView mv = new ModelAndView("projeto/listaProjeto");
		Iterable<Projeto> projetos = pr.findAll();
		mv.addObject("projetos", projetos);
		return mv;
	}

	//
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView detalhesProjeto(@PathVariable("id") long id) {
		Projeto projeto = pr.findById(id);
		ModelAndView mv = new ModelAndView("projeto/detalhesProjeto");
		mv.addObject("projeto", projeto);

		Iterable<Aula> aulas = ar.findByProjeto(projeto);
		mv.addObject("aulas", aulas);

		return mv;
	}

	// DELETA PROJETO
	@RequestMapping("/deletarProjeto")
	public String deletarProjeto(long id) {
		Projeto projeto = pr.findById(id);
		pr.delete(projeto);
		return "redirect:/projetos";
	}

	// ADICIONAR AULA
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public String detalhesProjetoPost(@PathVariable("id") long id, @Valid Aula aula, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{id}";
		}

		Projeto projeto = pr.findById(id);
		aula.setProjeto(projeto);
		ar.save(aula);
		attributes.addFlashAttribute("mensagem", "Aula adicionada com sucesso");
		return "redirect:/{id}";
	}

	// DELETA AULA PELO ID
	@RequestMapping("/deletarAula")
	public String deletarAula(@RequestParam("id") Long id) {
	    Aula aula = ar.getById(id);  // Usando Long diretamente
	    Projeto projeto = aula.getProjeto();
	    Long id_projeto = projeto.getId_projeto();  // Usando Long diretamente

	    ar.delete(aula);

	    return "redirect:/" + id_projeto;  // Redireciona para a página do projeto
	}

	// Métodos que atualizam projeto
	// formulário edição do projeto
	@RequestMapping(value = "editar-projeto", method = RequestMethod.GET)
	public ModelAndView editarProjeto(long id) {
		Projeto projeto = pr.findById(id);
		ModelAndView mv = new ModelAndView("projeto/update-projeto");
		mv.addObject("projeto", projeto);
		return mv;
	}

	// UPDATE PROJETO
	@RequestMapping(value = "/editar-projeto", method = RequestMethod.POST)
	public String updateProjeto(@Valid Projeto projeto, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
	        attributes.addFlashAttribute("error", "Erro ao atualizar o projeto!");
	        return "redirect:/editar-projeto?id=" + projeto.getId_projeto();
	    }
		projeto.setData_atualizacao(LocalDateTime.now());
		pr.save(projeto);
		attributes.addFlashAttribute("success", "Projeto alterado com sucesso!");

		long idLong = projeto.getId_projeto();
		String id = "" + idLong;
		return "redirect:/" + id;
	}

}