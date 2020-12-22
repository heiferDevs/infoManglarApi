package ec.gob.ambiente.api.showcase;

import java.util.ArrayList;
import java.util.List;

import ec.gob.ambiente.api.controller.UserInfoManglarController;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.model.PriceDaily;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;
import lombok.Getter;
import lombok.Setter;

public class ShowcaseUtil {

	public Org getOrg(PricesForm pricesForm, OfficialDocsForm officialDocsForm, EconomicIndicatorsForm economicIndicatorsForm, OrganizationsUserFacade organizationsUserFacade) {
		String name = officialDocsForm.getOrganizationName();
		String image = getFileForm(officialDocsForm.getFileForms(), "organizationLogo").getUrl();
		String principalActivity = ShowcaseUtil.getPrincipalActivity(economicIndicatorsForm);
		String summary = officialDocsForm.getSummary();
		Integer nSocios = getNSocios(organizationsUserFacade, officialDocsForm.getOrganizationManglarId());
		String creation = officialDocsForm.getYearCreation().toString();
		String contactSellerName = pricesForm.getName();
		String contactSellerMobile = pricesForm.getMobile();
		String contactSellerAddress = pricesForm.getAddress();
	    List<String> importantPoints = getImportantPoints(principalActivity, officialDocsForm.getOrganizationType(), nSocios);
	    List<Product> products = getProducts(pricesForm);
	    List<Service> services = getServices(pricesForm);
	    List<Project> projects = new ArrayList<>();
	    List<Document> documents = new ArrayList<>();
		return new Org(name, image, principalActivity, summary, nSocios, creation, importantPoints, products, services, projects, documents, contactSellerName, contactSellerMobile, contactSellerAddress);		
	}

	public static String getPrincipalActivity(EconomicIndicatorsForm economicIndicatorsForm){
		if (economicIndicatorsForm == null) return "Por favor llenar formulario indicadores económicos";
		return economicIndicatorsForm.getPrincipalActivity();
	}

	private Integer getNSocios(OrganizationsUserFacade organizationsUserFacade, Integer orgId) {
		List<User> usersByOrgAndRole = organizationsUserFacade.findByOrgIdAndRole(orgId, UserInfoManglarController.ROL_SOCIO_INFO_MANGLAR);
		if (usersByOrgAndRole == null) return 0;
		return usersByOrgAndRole.size();
	}

	private List<String> getImportantPoints(String principalActivity, String organizationType, Integer nSocios ){
		List<String> points = new ArrayList<>();
		points.add("Actividad principal: " + principalActivity);
		points.add("Tipo de organización: " + organizationType);
		points.add("Socios registrados: " + nSocios);
		return points;
	}

	private List<Service> getServices(PricesForm pricesForm){
		List<Service> services = new ArrayList<>();
		String productsKey = "Servicios";
		for ( PriceDaily priceDaily : pricesForm.getPriceDailies() ) {
			if ( productsKey.equals(priceDaily.getProductType()) ) {
				String image = getFileForm(priceDaily.getFileForms(), "productImage").getUrl();;
				String name = priceDaily.getBioemprendimientoName();
				String desc = "Bioemprendimiento";
				String price = "";
				services.add(new Service(image, name, desc, price));
			}
		}
		return services;
	}

	private List<Product> getProducts(PricesForm pricesForm){
		List<Product> products = new ArrayList<>();
		String productsKey = "Productos bioacuáticos";
		for ( PriceDaily priceDaily : pricesForm.getPriceDailies() ) {
			if ( productsKey.equals(priceDaily.getProductType()) ) {
				String image = getFileForm(priceDaily.getFileForms(), "productImage").getUrl();;
				String name = priceDaily.getBioAquaticType();
				String quality = getQuality(priceDaily);
				String price = priceDaily.getBioAquaticPrice().toString();
				Integer stock = getStock(priceDaily);
				Product product = new Product(image, name, quality, price, stock);
				products.add(product);
			}
		}
		return products;
	}

	private String getQuality(PriceDaily priceDaily){
		if (priceDaily.getShellQuality() != null ) return priceDaily.getShellQuality();
		if (priceDaily.getCrabQuality() != null ) return priceDaily.getCrabQuality();
		return "No especifico calidad";
	}

	private Integer getStock(PriceDaily priceDaily){
		if (priceDaily.getShellCount() != null ) return priceDaily.getShellCount();
		if (priceDaily.getSartsCount() != null ) return priceDaily.getSartsCount();
		if (priceDaily.getMielMangleCount() != null ) return priceDaily.getMielMangleCount();
		if (priceDaily.getCraftCount() != null ) return priceDaily.getCraftCount();
		if (priceDaily.getPlantasMangleCount() != null ) return priceDaily.getPlantasMangleCount();
		return 0;
	}

	private FileForm getFileForm(List<FileForm> fileForms, String idOption){
		for (FileForm fileForm : fileForms){
			if (idOption.equals(fileForm.getIdOption())){
				return fileForm;
			}
		}
		return new FileForm();
	}

}


class Org {
    
	@Getter
    @Setter
    String name;
    
	@Getter
    @Setter
    String image;
    
	@Getter
    @Setter
    String principalActivity;
    
	@Getter
    @Setter
    String summary;

    @Getter
    @Setter
    Integer nSocios;

    @Getter
    @Setter
    String creation;

    @Getter
    @Setter
    List<String> importantPoints;

    @Getter
    @Setter
    List<Product> products;

    @Getter
    @Setter
    List<Service> services;

    @Getter
    @Setter
    List<Project> projects;

    @Getter
    @Setter
    List<Document> documents;

	@Getter
    @Setter
    String contactSellerName;

	@Getter
    @Setter
    String contactSellerMobile;

	@Getter
    @Setter
    String contactSellerAddress;

	public Org(String name, String image, String principalActivity,
			String summary, Integer nSocios, String creation,
			List<String> importantPoints, List<Product> products,
			List<Service> services, List<Project> projects,
			List<Document> documents, String contactSellerName,
			String contactSellerMobile, String contactSellerAddress) {
		super();
		this.name = name;
		this.image = image;
		this.principalActivity = principalActivity;
		this.summary = summary;
		this.nSocios = nSocios;
		this.creation = creation;
		this.importantPoints = importantPoints;
		this.products = products;
		this.services = services;
		this.projects = projects;
		this.documents = documents;
		this.contactSellerName = contactSellerName;
		this.contactSellerMobile = contactSellerMobile;
		this.contactSellerAddress = contactSellerAddress;
	}

}


class Product {

    @Getter
    @Setter
    String image;

    @Getter
    @Setter
    String name;
    
    @Getter
    @Setter
    String quality;
    
    @Getter
    @Setter
    String price; // String like $18.00/Sarta | $10.00/Ciento

    @Getter
    @Setter
    Integer stock;

    public Product(String image, String name, String quality, String price,
        Integer stock) {
        super();
        this.image = image;
        this.name = name;
        this.quality = quality;
        this.price = price;
        this.stock = stock;
    }

}

class Service {

    @Getter
    @Setter
    String image;

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String desc;

    @Getter
    @Setter
    String price;

    public Service(String image, String name, String desc, String price) {
        super();
        this.image = image;
        this.name = name;
        this.desc = desc;
        this.price = price;
    }

}

class Project {

    @Getter
    @Setter
    String image;

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String desc;

    public Project(String image, String name, String desc) {
        super();
        this.image = image;
        this.name = name;
        this.desc = desc;
    }

}

class Document {

    @Getter
    @Setter
    String image;

    @Getter
    @Setter
    String name;

    @Getter
    @Setter
    String desc;

    public Document(String image, String name, String desc) {
        super();
        this.image = image;
        this.name = name;
        this.desc = desc;
    }

}