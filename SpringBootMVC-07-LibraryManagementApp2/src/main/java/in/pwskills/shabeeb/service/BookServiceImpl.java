package in.pwskills.shabeeb.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pwskills.shabeeb.dao.BookRepository;
import in.pwskills.shabeeb.model.Book;

@Service
public class BookServiceImpl implements IBookService {

	@Autowired
	private BookRepository repo;
	
	@Override
	public List<Book> findAllBooks() {
		// TODO Auto-generated method stub
		return StreamSupport.stream(repo.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public Book saveBook(Book book) {
		// TODO Auto-generated method stub
		return repo.save(book);
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void findBookById(Integer id) {
		// TODO Auto-generated method stub

	}

}
