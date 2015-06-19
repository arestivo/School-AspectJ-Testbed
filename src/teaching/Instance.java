package teaching;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import people.Student;
import people.Teacher;
import courses.Course;

public class Instance {
	private Course subject;
	private Collection<Teacher> teachers = new HashSet<Teacher>();
	private Collection<Student> students = new HashSet<Student>();
	private int year;
	
	private static Collection<Instance> instances = new LinkedList<Instance>();

	public Instance(Course subject, int year) {
		this.subject = subject;
		this.year = year;
	}
	
	public void setSubject(Course subject) {
		this.subject = subject;
	}

	public Course getCourse() {
		return subject;
	}
	
	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	public static void addInstance(Instance i) {
		instances.add(i);
	}

	public static Collection<Instance> getInstances() {
		return instances;
	}

	public boolean hasTeacher(Teacher t) {
		return teachers.contains(t);
	}

	public void removeTeacher(Teacher t) {
		teachers.remove(t);
	}
}
