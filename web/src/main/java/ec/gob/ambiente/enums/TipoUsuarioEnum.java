package ec.gob.ambiente.enums;

import lombok.Getter;

public enum TipoUsuarioEnum {
	SOCIO_MANGLAR("usuario_socio_maglar"),
	SOCIO_MANGLAR_ADMINISTRADOR("usuario_socio_maglar_administrador"),
	SOCIO_MANGLAR_ADMINISTRADOR_SUPER("usuario_socio_maglar_administrador_super"),
	;

	@Getter
    private String codigo;

    private TipoUsuarioEnum(String codigo) {            
       this.codigo=codigo;
    }

}
