package ec.gob.ambiente.util;

public enum StatusProcess {

	PENDING(0), ACTIVE(1), COMPLETED(2), ABORTED(3), SUSPENDED(4);

	private Integer status;

	private StatusProcess(Integer status) {	
		this.status = status;
	}

	public static String getNombreEstado(int status) {
		if (PENDING.status != null && PENDING.status == status) {
			return "Pendiente";
		} else if (ACTIVE.status != null && ACTIVE.status == status) {
			return "Activo";
		} else if (COMPLETED.status != null && COMPLETED.status == status) {
			return "Completado";
		} else if (ABORTED.status != null && ABORTED.status == status) {
			return "Abortado";
		} else if (SUSPENDED.status != null && SUSPENDED.status == status) {
			return "Suspendido";
		}
		return null;
	}
}