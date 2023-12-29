package cl.litscl.servletpaginacion.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cl.litscl.servletpaginacion.model.Persona;

/**
 * Servlet implementation class PaginacionControlador
 */
@WebServlet("/PaginacionControlador")
public class PaginacionControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaginacionControlador() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1. Obtener los registros necesarios (En un entorno real se deben conseguir desde la BD).
		List<Persona> personas = new ArrayList<Persona>();
		
		for (int i = 1; i <= 15; i++) {
			Persona p = new Persona();
			p.setId(i);
			p.setNombre("Nombre " + i);
			p.setEdad(i + 20);
			
			personas.add(p);
		}
		
		//2. Definir las variables necesarias para la paginación.
		int paginaActual = 0;
		int totalPaginas = 0;
		int totalResultados = personas.size();
		int resultadosPorPagina = 5;
		int indice = 0;
		
		//3. Obtener la página actual (Seleccionada por el usuario).
		try {
			paginaActual = Integer.parseInt(request.getParameter("pagina"));
		} catch (Exception ex) {
			paginaActual = 1; //Si el usuario no envía página o envía parámetros invalidos, se comienza desde la página 1.
		}		
		
		try {			
			indice = (paginaActual - 1) * (resultadosPorPagina);
			
			PrintWriter pw = response.getWriter();
			
			//4. Pintar en pantalla las columnas necesarias.
			pw.println("<center>");
			pw.println("<table border=3>");
			pw.println("<tr>");
			pw.println("<th>ID</th>");
			pw.println("<th>Nombre</th>");
			pw.println("<th>Edad</th>");
			pw.println("</tr>");
			
			//5. Pintar las celdas con los registros especificados (Facilmente podrían ser Cards).
			int contador = 0;
			for (Persona p : personas) {
				if (contador >= indice) {
					if (contador - indice == resultadosPorPagina) {
						break;
					}
					pw.println("<tr>");
					pw.println("<td>" + p.getId() + "</td>");
					pw.println("<td>" + p.getNombre() + "</td>");
					pw.println("<td>" + p.getEdad() + "</td>");
					pw.println("</tr>");			
				}
				contador++;				
			}
			
			pw.println("</table>");
			
			//6. Calcular el total de páginas necesarias.
			totalPaginas = totalResultados / resultadosPorPagina;
			
			if (totalResultados > totalPaginas * resultadosPorPagina) { //Verificando si se van a necesitar más páginas.
				totalPaginas++;
			}
			
			//7. Crear el menú de paginación (Permite seleccionar la página y en base a eso el servlet calcula desde que registro comenzar a mostrar).
			for (int i = 1; i <= totalPaginas; i++) {
				pw.println("<b><a href='PaginacionControlador?pagina=" + i + "'>" + i + "</a></b>");
				pw.println("&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			pw.println("</center>");
			pw.close();
		} catch (Exception ex) {
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
