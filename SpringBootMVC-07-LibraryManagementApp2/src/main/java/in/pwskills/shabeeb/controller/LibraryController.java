package in.pwskills.shabeeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import in.pwskills.shabeeb.model.Book;
import in.pwskills.shabeeb.service.IBookService;

@Controller
//@RequestMapping("/emp")
public class LibraryController {

	public LibraryController() {
		System.out.println("Library Controller is being created");
	}
	
	@Autowired(required=true)
	private IBookService service;

    
	
	@GetMapping("/")
	public String showPage() {
		System.out.println("LibraryController.showPage()");
		return "redirect:new-book";
	}
	
	@GetMapping("/new-book")
	public String showBookCreationForm(Model model) {
		System.out.println("LibraryController.showBookCreationForm()");
		model.addAttribute("book", new Book());
		return "new-book";
	}
	
	@PostMapping("/add")
	public String addNewBook(@ModelAttribute Book book, Model model) {
		System.out.println("LibraryController.addNewBook()");
		System.out.println(book);
		service.saveBook(book);
		model.addAttribute("books", service.findAllBooks());
		return "books";
	}
}
