package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.entity.Student;
import com.mvc.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	protected final transient Log log = LogFactory
			.getLog(StudentController.class);
	@Autowired
	private StudentService studentService;

	@RequestMapping("/list")
	public String load(ModelMap modelMap) {
		List<Object> list = studentService.getStudentList();
		modelMap.put("list", list);
		return "student";
	}

	@RequestMapping("/add")
	public String add(HttpServletRequest request, ModelMap modelMap)
			throws Exception {
		return "student_add";
	}

	@RequestMapping("/save")
	public String save(HttpServletRequest request, ModelMap modelMap) {
		String uname = request.getParameter("uname");
		String upsw = request.getParameter("upsw");
		Student st = new Student();
		st.setUname(uname);
		st.setUpsw(upsw);
		try {
			studentService.save(st);
			modelMap.put("addstate", "��ӳɹ���");
		} catch (Exception e) {
			log.error(e.getMessage());
			modelMap.put("addstate", "���ʧ�ܣ�");
		}

		return "student_add";
	}

	@RequestMapping("/del")
	public void del(@RequestParam("id") String id, HttpServletResponse response) {
		try {
			Student st = new Student();
			st.setId(Integer.valueOf(id));
			studentService.delete(st);
			response.getWriter().print("{\"del\":\"true\"}");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}
