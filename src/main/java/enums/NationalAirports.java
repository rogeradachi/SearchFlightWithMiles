package enums;

public enum NationalAirports {
	BSB("DF_Brasília"), CGH("SP_Congonhas"), GIG("RJ_Galeão"), SSA("BA_Salvador"), FLN("SC_Florianópolis"), POA("RS_Porto_Alegre"), VCP("SP_Campinas"), REC("PE_Recife"), CWB("PR_Curitiba"), BEL("PA_Belém"), VIX("ES_Vitória"), SDU(
			"RJ_Santos_Dumont"), CGB("MT_Cuiabá"), CGR("MS_Campo_Grande"), FOR("CE_Fortaleza"), MCP("AP_Macapá"), MGF("PR_Maringá"), GYN("GO_Goiânia"), NVT("SC_Navegantes"), MAO("AM_Manaus"), NAT("RN_Natal"), BPS("BA_Porto_Seguro"), MCZ(
			"AL_Maceió"), PMW("TO_Palmas"), SLZ("MA_São_Luis"), GRU("SP_Guarulhos"), LDB("PR_Londrina"), PVH("RO_Porto_Velho"), RBR("AC_Rio_Branco"), JOI("SC_Joinville"), UDI("MG_Uberlândia"), CXJ("RS_Caxias_do_Sul"), IGU(
			"PR_Foz_do_Iguaçu"), THE("PI_Teresina"), AJU("SE_Aracaju"), JPA("PB_João_Pessoa"), PNZ("PE_Petrolina"), CNF("MG_Confins"), BVB("RR_Boa_Vista"), CPV("PB_Campina_Grande"), STM("PA_Santarém"), IOS("BA_Ilhéus"), JDO(
			"CE_Juazeiro_do_Norte"), IMP("MA_Imperatriz"), XAP("SC_Chapecó"), MAB("PA_Marabá"), CZS("AC_Cruzeiro_do_Sul"), PPB("SP_Presidente_Prudente"), CFB("RJ_Cabo_Frio"), FEN("PE_Fernando_de_Noronha"), JTC("SP_Bauru"), MOC(
			"MG_Montes_Claros"), SAO("SP_Todos");

	private String value;

	public String code() {
		return value;
	}

	private NationalAirports(String airPortCode) {
		this.value = airPortCode;
	}
}
