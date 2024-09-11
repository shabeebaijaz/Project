package in.pwskills.shabeeb.service;

import java.util.List;

import in.pwskills.shabeeb.model.Book;

public interface IBookService {
	
	public List<Book> findAllBooks();
	public Book saveBook(Book book);
	public void deleteById(Integer id);
	public void findBookById(Integer id);

}
