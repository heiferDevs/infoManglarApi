package ec.gob.ambiente.util;

import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.suia.dto.ResumeTarea;
import ec.gob.ambiente.suia.dto.Tarea;
import ec.gob.ambiente.suia.dto.EstadoTarea;
import ec.gob.ambiente.suia.webservicesclientes.facade.JbpmSuiaCustomServicesFacade;

import org.kie.api.task.model.TaskSummary;

import java.text.SimpleDateFormat;

/**
 * <b> AGREGAR DESCRIPCION. </b>
 *
 * @author mayriliscs
 * @version Revision: 1.0
 *          <p>
 *          [Autor: mayriliscs, Fecha: 26/02/2015]
 *          </p>
 */
public final class ConvertidorObjetosDominioUtil {

    public static Tarea convertirTaskSummaryATarea(TaskSummary taskSummary, Tarea tarea) throws Exception {
        tarea.setId(taskSummary.getId());
        tarea.setNombre(taskSummary.getName());
        tarea.setEstado(EstadoTarea.getNombreEstado(taskSummary.getStatus().name()));
        String idUsuario = taskSummary.getActualOwner() != null ? taskSummary.getActualOwner().getId() : taskSummary.getCreatedBy().getId();
        
        String usuario = ((UserFacade) BeanLocator.getInstance(UserFacade.class)).getCompleteNameByUserName(idUsuario);
        
        tarea.setResponsable(usuario);
        
//        User usuario = ((UserFacade) BeanLocator.getInstance(UserFacade.class)).findByUserName(idUsuario);
//        if (usuario != null && usuario.getPeople() != null) {
//            OrganizationFacade organizacionFacade = (OrganizationFacade) BeanLocator.getInstance(OrganizationFacade.class);
//            Organization organizacion = organizacionFacade.findByPeopleAndRuc(usuario.getPeople(), usuario.getUserName());
//            if(organizacion != null){
//                 tarea.setResponsable(organizacion.getOrgaNameOrganization());
//            }else {
//                tarea.setResponsable(usuario.getPeople().getPeopName());
//            }
//        } else {
//                tarea.setResponsable("");            
//        }
        taskSummary.getActivationTime();
        tarea.setFechaInicio(taskSummary.getActivationTime());
        tarea.setFechaFin(((JbpmSuiaCustomServicesFacade) BeanLocator.getInstance(JbpmSuiaCustomServicesFacade.class))
                .getFechaFinTarea(taskSummary.getId()));
        return tarea;
    }


    /**
     * <b>
     * Metodo que convierte un resumen de tarea a una tarea.
     * </b>
     * <p>[Author: Javier Lucero, Date: 04/05/2015]</p>
     *
     * @param resumeTarea: resumen de tarea
     * @param tarea        : tarea
     * @return Tarea : tarea
     * @throws Exception: excepcion
     */
    public static Tarea convertirBamTaskSummaryATarea(ResumeTarea resumeTarea, Tarea tarea) throws Exception {
        tarea.setId(Long.valueOf(resumeTarea.getPk()));
        tarea.setNombre(resumeTarea.getTaskName());
        tarea.setEstado(EstadoTarea.getNombreEstado(resumeTarea.getStatus()));
        
        String usuario = ((UserFacade) BeanLocator.getInstance(UserFacade.class)).getCompleteNameByUserName(resumeTarea.getUserId());
        
        tarea.setResponsable(usuario);
        
//        User usuario = ((UserFacade) BeanLocator.getInstance(UserFacade.class)).findByUserName(resumeTarea.getUserId());
//        if(usuario != null && usuario.getPeople() != null)
//        	tarea.setResponsable(usuario.getPeople().getPeopName());
//        else{
//        	usuario = ((UserFacade) BeanLocator.getInstance(UserFacade.class)).findByUserNameDisabled(resumeTarea.getUserId());
//        	if(usuario != null && usuario.getPeople() != null)
//        		tarea.setResponsable(usuario.getPeople().getPeopName());
//        	else
//        		tarea.setResponsable("");
//        }
        
        SimpleDateFormat fechaInicio = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        tarea.setFechaInicio(fechaInicio.parse(resumeTarea.getCreatedDate()));
        SimpleDateFormat fechaFin = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        if(resumeTarea.getEndDate() != null)
        	tarea.setFechaFin(fechaFin.parse(resumeTarea.getEndDate()));
        return tarea;
    }
}
