package co.edu.uptc.project_1.exceptions;

public class ProjectExeption extends Exception {
    private TypeMessage typeMessage;

    public ProjectExeption(TypeMessage typeMessage) {
        super(typeMessage.message);
        this.typeMessage = typeMessage;
    }

    public Message getMenssage() {
        return new Message(this.typeMessage.code,
                this.typeMessage.message,
                this.typeMessage.codeHttp);

    }

}
