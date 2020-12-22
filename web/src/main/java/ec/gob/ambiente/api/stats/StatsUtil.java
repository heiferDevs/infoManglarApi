package ec.gob.ambiente.api.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.InvestmentsOrgsForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import lombok.Getter;
import lombok.Setter;

public class StatsUtil {

	public List<StatData> getResults(Map<String, Integer> stats){
		List<StatData> result = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : stats.entrySet()) {
		    String label = entry.getKey();
		    int value = entry.getValue();
		    result.add(new StatData(label, value));
		}
		return result;
	}

	public List<StatData> getResultsPercentage(Map<String, Integer> stats){
		List<StatData> result = getResults(stats);
		int total = 0;
		for (StatData statData : result) {
			total += statData.getValue();
		}
		for (StatData statData : result) {
			int value = (int) Math.round((new Double(statData.getValue()) / new Double(total)) * 100.0);
			System.out.println("value " + value);
			statData.setValue(value);
		}
		return result;
	}

	public Double getTotalInvestment(InvestmentsOrgsForm form){
		double total = 0;
		if (form.getSurveillanceControl() != null)  total += form.getSurveillanceControl();
		if (form.getAdministrativesExpenses() != null)  total += form.getAdministrativesExpenses();
		if (form.getSustainableUse() != null)  total += form.getSustainableUse();
		if (form.getSocialActivities() != null)  total += form.getSocialActivities();
		if (form.getMonitoring() != null)  total += form.getMonitoring();
		if (form.getCapacitation() != null)  total += form.getCapacitation();
		if (form.getReforestation() != null)  total += form.getReforestation();
		if (form.getInfrastructure() != null)  total += form.getInfrastructure();
		return total;
	}

	public List<StatData> getResultsAverage(Map<String, Integer> statsCount, Map<String, Double> statsValue){
		List<StatData> result = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : statsCount.entrySet()) {
		    String label = entry.getKey();
		    int count = entry.getValue();
		    double value = statsValue.get(label);
		    int average = (int) Math.round( value / new Double(count));
		    result.add(new StatData(label, average));
		}
		return result;
	}

	public double getFishingEffortShell(ShellCollectionForm shellCollectionForm){
		Integer countEffort = getCount(shellCollectionForm.getSimilisShellsCount(), shellCollectionForm.getTuberculosasShellsCount());
		return getFishingEffort(countEffort, shellCollectionForm.getHoursWorked());
	}

	public double getFishingEffortCrab(CrabCollectionForm crabCollectionForm){
		Integer countEffort = getCount(crabCollectionForm.getCrabCollectedCount());
		return getFishingEffort(countEffort, crabCollectionForm.getHoursWorked());
	}

	public double getFishingEffort(Integer count, Integer hours){
		if (count == null || hours == null || count.equals(0) || hours.equals(0)) return 0.0;
		return new Double(new Double(count)/new Double(hours));
	}

	private int getCount(Integer ...counts){
		int count = 0;
		for (Integer c : counts) {
			if (c != null) count += c;
		}
		return count;
	}

	public Double getValueActivity(String average){
		switch( average ){
			case "menor a $5":
				return 2.5;
			case "$5 a $10":
				return 7.5;
			case "$11 a $20":
				return 15.0;
			case "$21 a $30":
				return 25.0;
			case "$31 a $40":
				return 35.0;
			case "mayor a $40":
				return 45.0;
		}
		return 0.0;
	}

}


class StatData {
	@Getter
	@Setter
	String label;
	@Getter
	@Setter
	int value;
	public StatData(String label, int value) {
		super();
		this.label = label;
		this.value = value;
	}
}
