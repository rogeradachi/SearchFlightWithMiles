package enums;

public enum NationalAirports {
	GRU("SP_Guarulhos"), CGH("SP_Congonhas"), SAO("SP_Todos"), VCP("SP_Viracopos"), FLN("SC_Florianopolis"), GYN("GO_Goiania"), CFN("MG_Confins"), PMP(
			"MG_Pampulha"), POA("RS_PortoAlegre"), CXS("RS_CaxiasDoSul");

	private String value;

	public String code() {
		return value;
	}

	private NationalAirports(String airPortCode) {
		this.value = airPortCode;
	}
}
