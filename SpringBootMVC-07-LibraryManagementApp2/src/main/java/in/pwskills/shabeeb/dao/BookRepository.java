package in.pwskills.shabeeb.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.pwskills.shabeeb.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

}
