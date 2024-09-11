package in.pwskills.shabeeb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.pwskills.shabeeb.dao.IEmployeeDao;
import in.pwskills.shabeeb.model.Employee;

@Service
public class EmployeeServiceImpl implements IEmployeeService{

	@Autowired
	private IEmployeeDao dao;
	
	@Override
	public void saveEmployee(Employee employee) {
		dao.save(employee);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Employee> findAllEmployees() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Employee findEmployeeById(Integer id) {
		// TODO Auto-generated method stub
		Optional<Employee> optional = dao.findById(id);
		Employee employee=null;
		if(optional.isPresent()) {
			employee = optional.get();
		}else {
			throw new RuntimeException("Unable to find the id:" +id);
		}
		
		return employee;
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		dao.deleteById(id);
	}

}
