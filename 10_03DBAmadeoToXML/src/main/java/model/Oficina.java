package model;

import java.util.List;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@XmlRootElement(namespace = "org.oficina")
public class Oficina {
	
	private List<Empleado> empleados;
	private List<Departamento> departamentos;

	@XmlElementWrapper(name="empleados")
	@XmlElement(name="empleado")
	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	@XmlElementWrapper(name="departamentos")
	@XmlElement(name="departamento")
	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}

}
