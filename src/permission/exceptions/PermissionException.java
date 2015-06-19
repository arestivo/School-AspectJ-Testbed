package permission.exceptions;

import com.feup.contribution.aida.annotations.PackageName;

@PackageName("permission")
public class PermissionException extends RuntimeException {
	public static String NEEDS_LOGIN = "You need to be logged in to do that";
	public static String NEEDS_TEACHER = "You need to be a teacher to do that";
	public static String NEEDS_INSTANCE_TEACHER = "You need to be a teacher of this instance to do that";
	public static String NEEDS_ADMIN = "You need to be an admin to do that";	
	
	public PermissionException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 5238639677144783351L;

}
