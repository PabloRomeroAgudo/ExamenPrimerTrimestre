package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.xml.bind.JAXBException;
import model.Departamento;
import model.Empleado;
import model.Oficina;
import util.Util;

public class EmpleadoDao {

	private Connection conn = null;

	private final String QUERY = """
				SELECT e.id AS id,
					e.nombre AS nombre,
					salario AS salario,
					nacido AS nacido,
					e.departamento AS idD,
					d.nombre AS nombreD
				FROM empleado e
					LEFT JOIN departamento d ON e.departamento = d.id
			""";
	
	private JaxbController controller = JaxbController.getInstance();
	private final static String FILE = "empleados.xml";

	/**
	 * Constructor
	 */
	public EmpleadoDao() {
		conn = BD.getConnection();
	}

	/**
	 * Cierra la conexión
	 */
	public void close() {
		BD.close();
	}

	/**
	 * Devuelve todos los empleados
	 * 
	 * @return lista de empleados
	 */
	public List<Empleado> findAll() {
		List<Empleado> emps = new ArrayList<>();
		String sql = QUERY;
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				emps.add(read(rs));
			}
		} catch (SQLException e) {
		}
		return emps;
	}

	/**
	 * Buscar un empleado conociendo su identificador
	 * 
	 * @param id del empleado
	 * @return empleado o null
	 */
	public Empleado findById(Integer id) {
		String sql = QUERY + "WHERE e.id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return read(rs);
			}
		} catch (SQLException e) {
		}
		return null;
	}

	/**
	 * Buscar empleados conociendo los primeros caracteres de su nombre
	 * 
	 * @param inicio del nombre
	 * @return lista de empleados que cumplen con la condición
	 */
	public List<Empleado> findByName(String inicio) {
		String sql = QUERY + "WHERE e.nombre LIKE ?";
		List<Empleado> emps = new ArrayList<Empleado>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, inicio + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				emps.add(read(rs));
			}
		} catch (SQLException e) {
		}
		return emps;
	}

	/**
	 * Añade un nuevo empleado
	 * 
	 * @param empleado
	 * @return true si ha sido añadido, false en caso contrario
	 */
	public boolean create(Empleado e) {
		String sql = """
				INSERT INTO empleado (nombre, salario, nacido)
				VALUES (?, ?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, e.getNombre());
			ps.setDouble(2, e.getSalario());
			if (e.getNacido() == null) {
				ps.setString(3, null);
			} else {
				ps.setString(3, e.getNacido().toString());
			}
			return ps.executeUpdate() > 0;
		} catch (SQLException ex) {
		}
		return false;
	}

	/**
	 * Modificar un empleado
	 * 
	 * @param empleado
	 * @return true si ha sido modificado, false en caso contrario
	 */
	public boolean update(Empleado e) {
		String sql = """
				UPDATE empleado
				SET nombre = ?, salario = ?, nacido = ?, departamento = ?
				WHERE id = ?
				""";
		try {
			// Si el empleado era jefe de otro departamento lo quito de jefe
			Empleado oldE = findById(e.getId());
			if (oldE.getDepartamento().getId() != e.getDepartamento().getId()) {
				DepartamentoDao daoD = new DepartamentoDao();
				Departamento d = daoD.findById(oldE.getDepartamento().getId());
				if (d != null && d.getJefe() != null && d.getJefe().getId() == e.getId()) {
					d.setJefe(null);
					daoD.update(d);
				}
			}
			// Ahora ya puedo actualizar
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, e.getNombre());
			ps.setDouble(2, e.getSalario());
			ps.setString(3, e.getNacido().toString());
			ps.setInt(4, e.getDepartamento().getId());
			ps.setInt(5, e.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException ex) {
		}
		return false;
	}

	/**
	 * Borra un empleado conociendo su identificador Si el empleado es jefe
	 * departamento se le deasigna
	 * 
	 * @param id del empleado
	 * @return true si es borrado, false en caso contrario
	 */
	public boolean delete(int id) {
		try {
			String sql = """
					UPDATE departamento
					SET jefe = NULL
					WHERE jefe = ?
					""";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			sql = """
					DELETE FROM empleado
					WHERE id = ?
					""";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}
	
	public Boolean writeToXML() {
		List<Empleado> lista = findAll();
		Oficina oficina = new Oficina();
		oficina.setEmpleados(lista);
		try {
			controller.objectToXML(oficina);
			controller.writeXMLFile(FILE);
			
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * Leer un departamento
	 * 
	 * @param ResultSet
	 * @return departamento
	 * 
	 */
	private Empleado read(ResultSet rs) {
		try {
			Integer id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			Double salario = rs.getDouble("salario");
			LocalDate nacido = Util.string2date(rs.getString("nacido"));
			Integer departamento = rs.getInt("idD");
			String nombreDepartamento = rs.getString("nombreD");
			Departamento d = Departamento.builder().id(departamento).nombre(nombreDepartamento).build();
			return new Empleado(id, nombre, salario, nacido, d);
		} catch (SQLException e) {
		}
		return null;
	}

}
