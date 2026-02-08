package com.git.devdioge.paciente_api.docs.classerepresentacioanal;

public class PacienteRepresentacional {

    public static final String BAD_REQUEST_400 = """         
            {
                "statusCode": 400,
                "mensagem": "Campo obrigatória está null ou vázio",
                "mensagemUsuario": "Campo obrigatório está inválido.",
                "classeException": "IllegalArgumentException",
                "timeStamp": "2026-02-08T10:42:19.0832846"
            }
            
            """;

    public static final String NOT_FOUND_404 = """
            
            {
                "statusCode": 404,
                "mensagem": "Paciente de id [?] não localizado no banco de dados.",
                "mensagemUsuario": "O id passado não existe no banco de dados, passe um id válido.",
                "classeException": "PacienteNaoLocalizadoException",
                "timeStamp": "2026-02-08T11:27:45.2638271"
            }
            
            """;

    public static final String NOT_FOUND_CODIGO_404 = """
            
            {
                "statusCode": 404,
                "mensagem": "Paciente de codigo [?] não localizado no banco de dados.",
                "mensagemUsuario": "O id passado não existe no banco de dados, passe um id válido.",
                "classeException": "PacienteNaoLocalizadoException",
                "timeStamp": "2026-02-08T11:27:45.2638271"
            }
            
            """;

    public static final String NOT_CONTENT= "";


}
