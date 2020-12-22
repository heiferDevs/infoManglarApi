package ec.gob.ambiente.api.history;

import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.model.HistoryChange;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

public class HistoryChangeUtil {

	public boolean save(HistoryChangeFacade historyChangeFacade, UserFacade userFacade,
			Integer userId, Integer formId, String formType, String typeChange) {
		HistoryChange historyChange = new HistoryChange();
		historyChange.setUserId(userId);
		historyChange.setFormId(formId);
		historyChange.setFormType(formType);
		historyChange.setTypeChange(typeChange);
		User user = userFacade.find(userId);
		historyChange.setUserName(user.getPeople().getPeopName());
		return historyChangeFacade.save(historyChange);
	}

}
