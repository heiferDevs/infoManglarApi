package ec.gob.ambiente.enums;

import lombok.Getter;

public enum TipoOrganizacionesPublicasPrivadas {
	
	EMPRESA_PRIVADA(6),
	EMPRESA_PUBLICA(7);	
	
	@Getter
    private Integer idTipoEmpresa;
	
	private TipoOrganizacionesPublicasPrivadas(Integer idTipoEmpresa) {
	       this.idTipoEmpresa = idTipoEmpresa;
	}
}