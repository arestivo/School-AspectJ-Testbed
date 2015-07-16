package attendance;

import java.util.LinkedList;
import java.util.List;

import com.feup.contribution.aida.annotations.PackageName;

import people.Student;
import schedule.Lecture;;

@PackageName("attendance")
public aspect Attendance {
	private List<Student> Lecture.attendees = new LinkedList<Student>();
	
	public void Lecture.addAttendance(Student student) {
		attendees.add(student);
	}

	public void Lecture.removeAttendance(Student student) {
		attendees.remove(student);
	}
	
	public boolean Lecture.hasAttended(Student student) {
		return attendees.contains(student);
	}	
}
