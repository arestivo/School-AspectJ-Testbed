package people.exceptions;

import com.feup.contribution.aida.annotations.PackageName;

@PackageName("people")
public class PersonAlreadyExists extends RuntimeException {
	private static final long serialVersionUID = 6758430647883934108L;
}
