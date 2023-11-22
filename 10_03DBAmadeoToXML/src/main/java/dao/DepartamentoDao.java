package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Departamento;
import model.Empleado;
import util.Util;

public class DepartamentoDao {

	private Connection conn = null;
	
	private final String QUERY = """
				SELECT d.id AS id,
						d.nombre AS nombre,
						jefe,
						e.nombre AS nombreJefe
				FROM departamento d
					LEFT JOIN empleado e ON d.jefe = e.id
			""";

	/**
	 * Constructor
	 */
	public DepartamentoDao() {
		conn = BD.getConnection();
	}

	/**
	 * Cierra la conexión
	 */
	public void close() {
		BD.close();
	}

	/**
	 * Devuelve todos los departamentos
	 * 
	 * @return lista de departamentos
	 */
	public List<Departamento> findAll() {
		List<Departamento> deps = new ArrayList<>();
		String sql = QUERY;
		try {
			ResultSet rs = conn.createStatement().executeQuery(sql);
			while (rs.next()) {
				deps.add(read(rs));
			}
		} catch (SQLException e) {
		}
		return deps;
	}

	/**
	 * Buscar un departamento conociendo su identificador
	 * 
	 * @param id del departamento
	 * @return departamento o null
	 */
	public Departamento findById(Integer id) {
		String sql = QUERY + "WHERE d.id = ?";
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
	 * Buscar departamentos conociendo los primeros caracteres de su nombre
	 * 
	 * @param inicio del nombre
	 * @return lista de departamentos que cumplen con la condición
	 */
	public List<Departamento> findByName(String inicio) {
		String sql = QUERY + "WHERE d.nombre LIKE ?";
		List<Departamento> deps = new ArrayList<Departamento>();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, inicio + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				deps.add(read(rs));
			}
		} catch (SQLException e) {
		}
		return deps;
	}

	/**
	 * Añade un nuevo departamento
	 * 
	 * @param departamento
	 * @return true si ha sido añadido, false en caso contrario
	 */
	public boolean create(Departamento d) {
		String sql = """
				INSERT INTO departamento (nombre, jefe)
				VALUES (?, ?)
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, d.getNombre());
			if (d.getJefe() == null) {
				ps.setObject(2, null);
			} else {
				ps.setInt(2, d.getJefe().getId());
			}
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return false;
	}

	/**
	 * Modificar un departamento
	 * 
	 * @param departamento
	 * @return true si ha sido modificado, false en caso contrario
	 */
	public boolean update(Departamento d) {
		String sql = """
				UPDATE departamento
				SET nombre = ?, jefe = ?
				WHERE id = ?
				""";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, d.getNombre());
			if (d.getJefe() == null) {
				ps.setObject(2, null);
			} else {
				// Asigno al empleado que va a ser jefe al departamento
				EmpleadoDao daoEmpleado = new EmpleadoDao();
				Empleado e = daoEmpleado.findById(d.getJefe().getId());
				if (e != null) {
					e.setDepartamento(d);
					daoEmpleado.update(e);
				}
				ps.setInt(2, d.getJefe().getId());
			}
			ps.setInt(3, d.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * Borra un departamento conociendo su identificador Los empleados del
	 * departamento se quedan sin departamento
	 * 
	 * @param id del departamento
	 * @return true si es borrado, false en caso contrario
	 */
	public boolean delete(Integer id) {
		PreparedStatement ps;
		try {
			// Quitar los empleados del departamento
			String sql = """
					UPDATE empleado
					SET departamento = NULL
					WHERE departamento = ?
					""";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			// Borrar el departamento
			sql = """
					DELETE FROM departamento
					WHERE id = ?
					""";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
		}
		return false;
	}

	/**
	 * Leer un departamento
	 * 
	 * @param ResultSet
	 * @return departamento
	 * 
	 */
	private Departamento read(ResultSet rs) {
		try {
			Integer id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			Integer jefe = rs.getInt("jefe");
			String nombreJefe = rs.getString("nombreJefe");
			Empleado e = Empleado.builder().id(jefe).nombre(nombreJefe).build();
			return new Departamento(id, nombre, e);
		} catch (SQLException e) {
		}
		return null;
	}

	/**
	 * Devuelve una lista de empleados de un departamento
	 * 
	 * @param id del departamento
	 * @return lista de empleados del departamento
	 */
	public List<Empleado> getEmpleados(Integer id) {
		List<Empleado> emps = new ArrayList<Empleado>();

		String sql = """
				SELECT id, nombre, salario, nacido, departamento
				FROM empleado
				WHERE departamento = ?
				""";
		try {
			Departamento d = findById(id);

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Empleado e = Empleado.builder()
						.id(rs.getInt("id"))
						.nombre(rs.getString("nombre"))
						.salario(rs.getDouble("salario"))
						.nacido(Util.string2date(rs.getString("nacido")))
						.departamento(d)
						.build();
				emps.add(e);
			}
		} catch (SQLException e) {
		}
		return emps;
	}

}
