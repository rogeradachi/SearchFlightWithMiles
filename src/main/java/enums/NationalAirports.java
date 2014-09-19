package enums;

public enum NationalAirports {
	SP_Guarulhos("GRU"),SP_Congonhas("CGN"),SP_TODOS("SAO"),SP_Viracopos("VCP"), SC_Florianopolis("FLN"), GO_Goiania(
			"GYN"), MG_Confins("CFN"), MG_Pampulha("PMP"), RS_PortoAlegre("POA"), RS_CaxiasDoSul("CXS");

	private String value;

	public String code() {
		return value;
	}

	private NationalAirports(String airPortCode) {
		this.value = airPortCode;
	}
}
