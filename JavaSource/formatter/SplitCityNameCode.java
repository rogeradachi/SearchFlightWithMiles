package formatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SplitCityNameCode {

	private static final String DATA_CODE = "data-code=";
	private final String destinationList = "<li style='display: none;' data-code='ACA' class='AMERICAlist'> Acapulco - Juan N. Alvarez Internacional&nbsp;(ACA)</li><li style='display: none;' data-code='ADZ' class='AMERICAlist'> San Andres Island - Gustavo R Pinilla International&nbsp;(ADZ)</li><li style='display: none;' data-code='AEP' class='AMERICAlist'> Buenos Aires - J. Newbery&nbsp;(AEP)</li><li style='display: none;' data-code='AGP' class='EUROPlist'> Malaga - Málaga Aeroporto&nbsp;(AGP)</li><li style='display: none;' data-code='AGT' class='AMERICAlist'> Ciudad del Este - Guarani International&nbsp;(AGT)</li><li style='display: list-item;' data-code='AJU' class='BRASILlist boxl'> Aracaju - Santa Maria&nbsp;(AJU)</li><li style='display: none;' data-code='AKL' class='ASIAlist'> Auckland - Auckland International&nbsp;(AKL)</li><li style='display: none;' data-code='ALG' class='ASIAlist'> Algiers - Houari Boumediene&nbsp;(ALG)</li><li style='display: none;' data-code='AMM' class='ASIAlist'> Amman - Queen Alia&nbsp;(AMM)</li><li style='display: none;' data-code='AMS' class='EUROPlist'> Amsterdam - Schiphol Airport&nbsp;(AMS)</li><li style='display: none;' data-code='ANF' class='AMERICAlist'> Antofagasta - Cerro Moreno&nbsp;(ANF)</li><li style='display: none;' data-code='APO' class='AMERICAlist'> Apartado - Carepa AR Betancourt&nbsp;(APO)</li><li style='display: none;' data-code='AQP' class='AMERICAlist'> Arequipa - Rodriguez Ballon International&nbsp;(AQP)</li><li style='display: none;' data-code='ARI' class='AMERICAlist'> Arica - Chacalluta&nbsp;(ARI)</li><li style='display: none;' data-code='ARN' class='EUROPlist'> Stockholm - Arlanda&nbsp;(ARN)</li><li style='display: none;' data-code='ASU' class='AMERICAlist'> Asuncion - Silvio Pettirossi International&nbsp;(ASU)</li><li style='display: none;' data-code='ATL' class='AMERICAlist'> Atlanta - Hartsfield Jackson Atlanta&nbsp;(ATL)</li><li style='display: none;' data-code='AUA' class='AMERICAlist'> Aruba - Reina Beatrix&nbsp;(AUA)</li><li style='display: none;' data-code='AUS' class='AMERICAlist'> Austin - Bergstrom Internacional&nbsp;(AUS)</li><li style='display: list-item;' data-code='AUX' class='BRASILlist'>Araguaína&nbsp;(AUX)</li><li style='display: none;' data-code='AXM' class='AMERICAlist'> Armenia - El Eden International&nbsp;(AXM)</li><li style='display: none;' data-code='BAQ' class='AMERICAlist'> Barranquilla - Ernesto Cortissoz International&nbsp;(BAQ)</li><li style='display: none;' data-code='BBA' class='AMERICAlist'>Balmaceda&nbsp;(BBA)</li><li style='display: none;' data-code='BCN' class='EUROPlist'> Barcelona - Aeroporto&nbsp;(BCN)</li><li style='display: none;' data-code='BDL' class='AMERICAlist'> Hartford - Bradley Internacional&nbsp;(BDL)</li><li style='display: list-item;' data-code='BEL' class='BRASILlist'> Belém - Val De Cans International&nbsp;(BEL)</li><li style='display: none;' data-code='BGA' class='AMERICAlist'> Bucaramanga - Palonegro International&nbsp;(BGA)</li><li style='display: none;' data-code='BHI' class='AMERICAlist'> Bahia Blanca - Comandante Espora&nbsp;(BHI)</li><li style='display: none;' data-code='BHM' class='AMERICAlist'>Birmingham&nbsp;(BHM)</li><li style='display: list-item;' data-code='BHZ' class='BRASILlist'>Belo Horizonte&nbsp;(BHZ)</li><li style='display: none;' data-code='BIO' class='EUROPlist'> Bilbao - Bilbao Aeroporto&nbsp;(BIO)</li><li style='display: none;' data-code='BJS' class='ASIAlist'>Beijing&nbsp;(BJS)</li><li style='display: none;' data-code='BJX' class='AMERICAlist'> Leon Guanajuato - Del Bajío International&nbsp;(BJX)</li><li style='display: none;' data-code='BKK' class='ASIAlist'> Bangkok - Suvarnabhumi Internacional&nbsp;(BKK)</li><li style='display: none;' data-code='BOG' class='AMERICAlist'> Bogotá - El Dorado International&nbsp;(BOG)</li><li style='display: none;' data-code='BOS' class='AMERICAlist'> Boston - Logan Internacional&nbsp;(BOS)</li><li style='display: list-item;' data-code='BPS' class='BRASILlist'>Porto Seguro&nbsp;(BPS)</li><li style='display: list-item;' data-code='BRA' class='BRASILlist'>Barreiras&nbsp;(BRA)</li><li style='display: none;' data-code='BRC' class='AMERICAlist'> San Carlos Bariloche - Bariloche Internacional&nbsp;(BRC)</li><li style='display: none;' data-code='BRE' class='EUROPlist'>Bremen&nbsp;(BRE)</li><li style='display: none;' data-code='BRU' class='EUROPlist'> Brussels - Brussels Airport&nbsp;(BRU)</li><li style='display: list-item;' data-code='BSB' class='BRASILlist'> Brasília - J.Kubitschek International&nbsp;(BSB)</li><li style='display: none;' data-code='BUE' class='AMERICAlist'>Buenos Aires&nbsp;(BUE)</li><li style='display: list-item;' data-code='BVB' class='BRASILlist'>Boa Vista&nbsp;(BVB)</li><li style='display: list-item;' data-code='CAC' class='BRASILlist'>Cascavel&nbsp;(CAC)</li><li style='display: none;' data-code='CAS' class='ASIAlist'> Casablanca - Anfa&nbsp;(CAS)</li><li style='display: none;' data-code='CCP' class='AMERICAlist'> Concepcion - Carriel Sur&nbsp;(CCP)</li><li style='display: none;' data-code='CCS' class='AMERICAlist'> Caracas - Simon Bolivar International&nbsp;(CCS)</li><li style='display: none;' data-code='CDG' class='EUROPlist'> Paris - Charles De Gaulle&nbsp;(CDG)</li><li style='display: none;' data-code='CEN' class='AMERICAlist'> Ciudad Obregon - International&nbsp;(CEN)</li><li style='display: list-item;' data-code='CGB' class='BRASILlist'> Cuiabá - Marechal Rondon International&nbsp;(CGB)</li><li style='display: list-item;' data-code='CGH' class='BRASILlist'> São Paulo - Congonhas&nbsp;(CGH)</li><li style='display: list-item;' data-code='CGR' class='BRASILlist'> Campo Grande - Campo Grande International&nbsp;(CGR)</li><li style='display: none;' data-code='CHS' class='AMERICAlist'> Charleston - International AFB&nbsp;(CHS)</li><li style='display: none;' data-code='CIX' class='AMERICAlist'> Chiclayo - J A Quinones Gonzalez&nbsp;(CIX)</li><li style='display: none;' data-code='CJA' class='AMERICAlist'> Cajamarca - Armando R Iglesias&nbsp;(CJA)</li><li style='display: none;' data-code='CJC' class='AMERICAlist'>Calama&nbsp;(CJC)</li><li style='display: none;' data-code='CLE' class='AMERICAlist'> Cleveland - Hopkins Internacional&nbsp;(CLE)</li><li style='display: none;' data-code='CLO' class='AMERICAlist'> Cali - Alfonso B Aragon International&nbsp;(CLO)</li><li style='display: none;' data-code='CMH' class='AMERICAlist'> Columbus - Port Columbus&nbsp;(CMH)</li><li style='display: list-item;' data-code='CNF' class='BRASILlist'> Belo Horizonte - Tancredo Neves International&nbsp;(CNF)</li><li style='display: none;' data-code='COR' class='AMERICAlist'> Cordoba - Pajas Blancas&nbsp;(COR)</li><li style='display: none;' data-code='CPH' class='EUROPlist'> Copenhagen - Kastrup&nbsp;(CPH)</li><li style='display: none;' data-code='CPO' class='AMERICAlist'> Copiapo - Desierto De Atacama&nbsp;(CPO)</li><li style='display: none;' data-code='CRD' class='AMERICAlist'> Comodoro Rivadavia - General E.Mosconi&nbsp;(CRD)</li><li style='display: none;' data-code='CTG' class='AMERICAlist'> Cartagena - Rafael Nunez International&nbsp;(CTG)</li><li style='display: none;' data-code='CUC' class='AMERICAlist'> Cucuta - Camilo Daza&nbsp;(CUC)</li><li style='display: none;' data-code='CUE' class='AMERICAlist'> Cuenca - Mariscal Lamar International&nbsp;(CUE)</li><li style='display: none;' data-code='CUN' class='AMERICAlist'> Cancún - International&nbsp;(CUN)</li><li style='display: none;' data-code='CUZ' class='AMERICAlist'> Cusco - A Velasco Astete International&nbsp;(CUZ)</li><li style='display: none;' data-code='CVG' class='AMERICAlist'> Cincinnati - Nth Kentucky&nbsp;(CVG)</li><li style='display: list-item;' data-code='CWB' class='BRASILlist'> Curitiba - Afonso Pena&nbsp;(CWB)</li><li style='display: none;' data-code='DCA' class='AMERICAlist'> Washington - R Reagan National&nbsp;(DCA)</li><li style='display: none;' data-code='DEL' class='ASIAlist'> Delhi - Indira Gandhi Internacional&nbsp;(DEL)</li><li style='display: none;' data-code='DEN' class='AMERICAlist'> Denver - Denver Internacional&nbsp;(DEN)</li><li style='display: none;' data-code='DFW' class='AMERICAlist'> Dallas - Dallas Ft Worth International&nbsp;(DFW)</li><li style='display: none;' data-code='DOH' class='ASIAlist'> Doha - Hamad International&nbsp;(DOH)</li><li style='display: list-item;' data-code='DOU' class='BRASILlist'> Dourados - F.De Matos Pereira&nbsp;(DOU)</li><li style='display: none;' data-code='DTT' class='AMERICAlist'>Detroit&nbsp;(DTT)</li><li style='display: none;' data-code='DTW' class='AMERICAlist'> Detroit - Detroit Metro&nbsp;(DTW)</li><li style='display: none;' data-code='DUB' class='EUROPlist'> Dublin - Dublin International&nbsp;(DUB)</li><li style='display: none;' data-code='DUS' class='EUROPlist'> Düsseldorf - Aeroporto Internacional&nbsp;(DUS)</li><li style='display: none;' data-code='EGE' class='AMERICAlist'> Vail Eagle - Eagle County&nbsp;(EGE)</li><li style='display: none;' data-code='EOH' class='AMERICAlist'> Medellin - Olaya Herrera&nbsp;(EOH)</li><li style='display: none;' data-code='ESR' class='AMERICAlist'> El Salvador - Ricardo Garcia Posada&nbsp;(ESR)</li><li style='display: none;' data-code='EWR' class='AMERICAlist'> Nova Iorque - Newark Liberty Internacional&nbsp;(EWR)</li><li style='display: none;' data-code='EYP' class='AMERICAlist'> El Yopal - El Alcaravan&nbsp;(EYP)</li><li style='display: none;' data-code='EYW' class='AMERICAlist'> Key West - Key West Internacional&nbsp;(EYW)</li><li style='display: none;' data-code='EZE' class='AMERICAlist'> Buenos Aires - Pistarini&nbsp;(EZE)</li><li style='display: none;' data-code='FCO' class='EUROPlist'> Roma - Fiumicino&nbsp;(FCO)</li><li style='display: list-item;' data-code='FLN' class='BRASILlist'> Florianópolis - Hercílio Luz&nbsp;(FLN)</li><li style='display: list-item;' data-code='FOR' class='BRASILlist'> Fortaleza - Pinto Martins International&nbsp;(FOR)</li><li style='display: none;' data-code='FRA' class='EUROPlist'> Frankfurt - Frankfurt Internacional&nbsp;(FRA)</li><li style='display: none;' data-code='FTE' class='AMERICAlist'> El Calafate - Coandante A.Tola&nbsp;(FTE)</li><li style='display: none;' data-code='GDL' class='AMERICAlist'> Guadalajara - Miguel Hidalgo International&nbsp;(GDL)</li><li style='display: list-item;' data-code='GIG' class='BRASILlist'> Rio de Janeiro - Galeão International&nbsp;(GIG)</li><li style='display: none;' data-code='GNV' class='AMERICAlist'> Gainesville - Gainesville Regional&nbsp;(GNV)</li><li style='display: none;' data-code='GPS' class='AMERICAlist'> Baltra Island - Seymour&nbsp;(GPS)</li><li style='display: list-item;' data-code='GRU' class='BRASILlist'> São Paulo - Guarulhos International&nbsp;(GRU)</li><li style='display: none;' data-code='GSO' class='AMERICAlist'> Greensboro/High Point - Piedmont Triad Internacional&nbsp;(GSO)</li><li style='display: none;' data-code='GVA' class='EUROPlist'> Geneva - Geneva Internacional&nbsp;(GVA)</li><li style='display: none;' data-code='GYE' class='AMERICAlist'> Guayaquil - Jose Joaquin De Olmedo&nbsp;(GYE)</li><li style='display: list-item;' data-code='GYN' class='BRASILlist'> Goiânia - Santa Genoveva&nbsp;(GYN)</li><li style='display: none;' data-code='HAM' class='EUROPlist'>Hamburg&nbsp;(HAM)</li><li style='display: none;' data-code='HAV' class='AMERICAlist'> Havana - Jose Marti Internacional&nbsp;(HAV)</li><li style='display: none;' data-code='HKG' class='ASIAlist'> Hong Kong - Hong Kong Internacional&nbsp;(HKG)</li><li style='display: none;' data-code='IAD' class='AMERICAlist'> Washington - Dulles Internacional&nbsp;(IAD)</li><li style='display: none;' data-code='IAH' class='AMERICAlist'> Houston - G.Bush Intercontinental&nbsp;(IAH)</li><li style='display: none;' data-code='IBE' class='AMERICAlist'> Ibague - Perales&nbsp;(IBE)</li><li style='display: none;' data-code='ICN' class='ASIAlist'> Seoul - Incheon Internacional&nbsp;(ICN)</li><li style='display: none;' data-code='IGR' class='AMERICAlist'> Iguazu - Cataratas Del Iguazu&nbsp;(IGR)</li><li style='display: list-item;' data-code='IGU' class='BRASILlist'> Foz Do Iguacu - Cataratas&nbsp;(IGU)</li><li style='display: list-item;' data-code='IMP' class='BRASILlist'> Imperatriz - Renato Moreira&nbsp;(IMP)</li><li style='display: none;' data-code='IND' class='AMERICAlist'> Indianapolis - Indianapolis Internacional&nbsp;(IND)</li><li style='display: list-item;' data-code='IOS' class='BRASILlist'> Ilhéus - Jorge Amado&nbsp;(IOS)</li><li style='display: none;' data-code='IPC' class='AMERICAlist'> Ilha de Páscoa - Mataveri Internacional&nbsp;(IPC)</li><li style='display: none;' data-code='IQQ' class='AMERICAlist'> Iquique - Diego Aracena&nbsp;(IQQ)</li><li style='display: none;' data-code='IQT' class='AMERICAlist'> Iquitos - F Secada Vignetta&nbsp;(IQT)</li><li style='display: none;' data-code='IST' class='ASIAlist'> Istanbul - Ataturk&nbsp;(IST)</li><li style='display: none;' data-code='JAX' class='AMERICAlist'> Jacksonville - Jacksonville Internacional&nbsp;(JAX)</li><li style='display: none;' data-code='JFK' class='AMERICAlist'> Nova Iorque - John F Kennedy International&nbsp;(JFK)</li><li style='display: list-item;' data-code='JOI' class='BRASILlist'> Joinville - Lauro Carneiro Loyola&nbsp;(JOI)</li><li style='display: list-item;' data-code='JPA' class='BRASILlist'> João Pessoa - Castro Pinto International&nbsp;(JPA)</li><li style='display: none;' data-code='JUL' class='AMERICAlist'> Juliaca - Inco Mnco Capac International&nbsp;(JUL)</li><li style='display: none;' data-code='LAS' class='AMERICAlist'> Las Vegas - McCarran Internacional&nbsp;(LAS)</li><li style='display: none;' data-code='LAX' class='AMERICAlist'> Los Angeles - Los Angeles Internacional&nbsp;(LAX)</li><li style='display: none;' data-code='LCG' class='EUROPlist'> A Corunha - A Coruña Aeroporto&nbsp;(LCG)</li><li style='display: list-item;' data-code='LDB' class='BRASILlist'>Londrina&nbsp;(LDB)</li><li style='display: none;' data-code='LET' class='AMERICAlist'> Leticia - Alfredo V Cobb International&nbsp;(LET)</li><li style='display: none;' data-code='LGA' class='AMERICAlist'> Nova Iorque - La Guardia&nbsp;(LGA)</li><li style='display: none;' data-code='LHR' class='EUROPlist'> Londres - Heathrow&nbsp;(LHR)</li><li style='display: none;' data-code='LIM' class='AMERICAlist'> Lima - Jorge Chavez International&nbsp;(LIM)</li><li style='display: none;' data-code='LIS' class='EUROPlist'> Lisbon - Aeroporto&nbsp;(LIS)</li><li style='display: none;' data-code='LON' class='EUROPlist'>Londres&nbsp;(LON)</li><li style='display: none;' data-code='LPB' class='AMERICAlist'> La Paz - El Alto International&nbsp;(LPB)</li><li style='display: none;' data-code='LSC' class='AMERICAlist'> La Serena - La Florida&nbsp;(LSC)</li><li style='display: list-item;' data-code='MAB' class='BRASILlist'> Marabá - Joao Correa Da Rocha&nbsp;(MAB)</li><li style='display: none;' data-code='MAD' class='EUROPlist'> Madrid - Adolfo Suarez Barajas&nbsp;(MAD)</li><li style='display: list-item;' data-code='MAO' class='BRASILlist'> Manaus - Eduardo Gomes International&nbsp;(MAO)</li><li style='display: none;' data-code='MCO' class='AMERICAlist'> Orlando - Orlando Internacional&nbsp;(MCO)</li><li style='display: list-item;' data-code='MCP' class='BRASILlist'> Macapá - Alberto Alcolumbre Int&nbsp;(MCP)</li><li style='display: list-item;' data-code='MCZ' class='BRASILlist'> Maceió - Zumbi dos Palmares&nbsp;(MCZ)</li><li style='display: none;' data-code='MDE' class='AMERICAlist'> Medellin - Jose Maria Cordova International&nbsp;(MDE)</li><li style='display: none;' data-code='MDZ' class='AMERICAlist'> Mendoza - El Plumerillo&nbsp;(MDZ)</li><li style='display: none;' data-code='MEM' class='AMERICAlist'> Memphis - Memphis Internacional&nbsp;(MEM)</li><li style='display: none;' data-code='MEX' class='AMERICAlist'> México - Benito Juarez Internacional&nbsp;(MEX)</li><li style='display: none;' data-code='MHC' class='AMERICAlist'> Castro - Mocopulli&nbsp;(MHC)</li><li style='display: none;' data-code='MIA' class='AMERICAlist'> Miami - Miami Internacional&nbsp;(MIA)</li><li style='display: none;' data-code='MSP' class='AMERICAlist'> Minneapolis - St Paul International&nbsp;(MSP)</li><li style='display: none;' data-code='MSY' class='AMERICAlist'> New Orleans - L Armstrong Internacional&nbsp;(MSY)</li><li style='display: none;' data-code='MTR' class='AMERICAlist'> Monteria - Los Garzones&nbsp;(MTR)</li><li style='display: none;' data-code='MTY' class='AMERICAlist'> Monterrey - General Mariano Escobedo International&nbsp;(MTY)</li><li style='display: none;' data-code='MUC' class='EUROPlist'> Munich - Munich Internacional&nbsp;(MUC)</li><li style='display: none;' data-code='MVD' class='AMERICAlist'> Montevideo - Carrasco&nbsp;(MVD)</li><li style='display: none;' data-code='MXP' class='EUROPlist'> Milan - Malpensa&nbsp;(MXP)</li><li style='display: none;' data-code='MZL' class='AMERICAlist'> Manizales - La Nubia&nbsp;(MZL)</li><li style='display: list-item;' data-code='NAT' class='BRASILlist'> Natal - International&nbsp;(NAT)</li><li style='display: none;' data-code='NGO' class='ASIAlist'> Nagoya - Chubu Centrair International&nbsp;(NGO)</li><li style='display: none;' data-code='NQN' class='AMERICAlist'> Neuquen - Presidente Peron&nbsp;(NQN)</li><li style='display: none;' data-code='NRT' class='ASIAlist'> Tokyo - Narita International&nbsp;(NRT)</li><li style='display: none;' data-code='NUE' class='EUROPlist'> Nuremberg - Aeroporto Nuremberga&nbsp;(NUE)</li><li style='display: none;' data-code='NVA' class='AMERICAlist'> Neiva - Benito Salas&nbsp;(NVA)</li><li style='display: list-item;' data-code='NVT' class='BRASILlist'>Navegantes&nbsp;(NVT)</li><li style='display: none;' data-code='NYC' class='AMERICAlist'>Nova Iorque&nbsp;(NYC)</li><li style='display: none;' data-code='OPO' class='EUROPlist'> Porto - Francisco SA Carneiro&nbsp;(OPO)</li><li style='display: list-item;' data-code='OPS' class='BRASILlist'> Sinop - Jao B.Figueiredo&nbsp;(OPS)</li><li style='display: none;' data-code='ORD' class='AMERICAlist'> Chicago - O Hare International&nbsp;(ORD)</li><li style='display: none;' data-code='ORF' class='AMERICAlist'> Norfolk - Norfolk Internacional&nbsp;(ORF)</li><li style='display: none;' data-code='OSL' class='EUROPlist'> Oslo - Gardermoen&nbsp;(OSL)</li><li style='display: none;' data-code='PAR' class='EUROPlist'>Paris&nbsp;(PAR)</li><li style='display: none;' data-code='PAZ' class='AMERICAlist'> Poza Rica - El Tajin&nbsp;(PAZ)</li><li style='display: none;' data-code='PCL' class='AMERICAlist'> Pucallpa - David Abenzur Rengifo International&nbsp;(PCL)</li><li style='display: none;' data-code='PDP' class='AMERICAlist'> Punta del Este - Punta Del Este&nbsp;(PDP)</li><li style='display: none;' data-code='PDX' class='AMERICAlist'> Portland - Portland Internacional&nbsp;(PDX)</li><li style='display: none;' data-code='PEI' class='AMERICAlist'> Pereira - Matecana International&nbsp;(PEI)</li><li style='display: none;' data-code='PEK' class='ASIAlist'> Beijing - Capital International&nbsp;(PEK)</li><li style='display: none;' data-code='PEM' class='AMERICAlist'> Puerto Maldonado - Padre Aldamiz International&nbsp;(PEM)</li><li style='display: none;' data-code='PHL' class='AMERICAlist'> Philadelphia - Philadelphia Internacional&nbsp;(PHL)</li><li style='display: none;' data-code='PHX' class='AMERICAlist'> Phoenix - Sky Harbor Internacional&nbsp;(PHX)</li><li style='display: none;' data-code='PIT' class='AMERICAlist'> Pittsburgh - Pittsburgh Internacional&nbsp;(PIT)</li><li style='display: none;' data-code='PIU' class='AMERICAlist'> Piura - G Concha Iberico International&nbsp;(PIU)</li><li style='display: list-item;' data-code='PLU' class='BRASILlist'> Belo Horizonte - Pampulha&nbsp;(PLU)</li><li style='display: none;' data-code='PMC' class='AMERICAlist'> Puerto Montt - El Tepual&nbsp;(PMC)</li><li style='display: none;' data-code='PMI' class='EUROPlist'>Palma Mallorca&nbsp;(PMI)</li><li style='display: list-item;' data-code='PMW' class='BRASILlist'> Palmas - Tocantins&nbsp;(PMW)</li><li style='display: none;' data-code='PNS' class='AMERICAlist'> Pensacola - International&nbsp;(PNS)</li><li style='display: list-item;' data-code='POA' class='BRASILlist'> Porto Alegre - Salgado Filho&nbsp;(POA)</li><li style='display: none;' data-code='PPT' class='ASIAlist'> Tahiti - Faaa&nbsp;(PPT)</li><li style='display: none;' data-code='PUJ' class='AMERICAlist'> Punta Cana - International&nbsp;(PUJ)</li><li style='display: none;' data-code='PUQ' class='AMERICAlist'> Punta Arenas - C.Ibanez Del Campo&nbsp;(PUQ)</li><li style='display: none;' data-code='PUU' class='AMERICAlist'> Puerto Asis - Tres De Mayo&nbsp;(PUU)</li><li style='display: none;' data-code='PVG' class='ASIAlist'> Shanghai - Pudong Internacional&nbsp;(PVG)</li><li style='display: list-item;' data-code='PVH' class='BRASILlist'> Porto Velho - Jorge T. Oliveira&nbsp;(PVH)</li><li style='display: list-item;' data-code='RAO' class='BRASILlist'> Ribeirão Preto - Leite Lopes&nbsp;(RAO)</li><li style='display: list-item;' data-code='RBR' class='BRASILlist'> Rio Branco - Placido De Castro International&nbsp;(RBR)</li><li style='display: none;' data-code='RDU' class='AMERICAlist'>Raleigh Durham&nbsp;(RDU)</li><li style='display: list-item;' data-code='REC' class='BRASILlist'> Recife - Guararapes&nbsp;(REC)</li><li style='display: none;' data-code='RGL' class='AMERICAlist'> Rio Gallegos - Piloto N.Fernandez&nbsp;(RGL)</li><li style='display: none;' data-code='RIC' class='AMERICAlist'> Richmond - Richmond Internacional&nbsp;(RIC)</li><li style='display: list-item;' data-code='RIO' class='BRASILlist'>Rio de Janeiro&nbsp;(RIO)</li><li style='display: none;' data-code='ROS' class='AMERICAlist'> Rosario - Islas Malvinas&nbsp;(ROS)</li><li style='display: none;' data-code='SAN' class='AMERICAlist'> San Diego - Lindberg Field&nbsp;(SAN)</li><li style='display: list-item;' data-code='SAO' class='BRASILlist'>São Paulo&nbsp;(SAO)</li><li style='display: none;' data-code='SCL' class='AMERICAlist'> Santiago - A Merino Benitez&nbsp;(SCL)</li><li style='display: none;' data-code='SCQ' class='EUROPlist'>Santiago de Compostela&nbsp;(SCQ)</li><li style='display: none;' data-code='SCY' class='AMERICAlist'> San Cristobal Island - San Cristobal&nbsp;(SCY)</li><li style='display: none;' data-code='SDF' class='AMERICAlist'> Louisville - Louisville Internacional&nbsp;(SDF)</li><li style='display: list-item;' data-code='SDU' class='BRASILlist'> Rio de Janeiro - Santos Dumont&nbsp;(SDU)</li><li style='display: none;' data-code='SEA' class='AMERICAlist'> Seattle - Seattle Tacoma International&nbsp;(SEA)</li><li style='display: none;' data-code='SEL' class='ASIAlist'>Seoul&nbsp;(SEL)</li><li style='display: none;' data-code='SFO' class='AMERICAlist'> San Francisco - San Francisco Internacional&nbsp;(SFO)</li><li style='display: none;' data-code='SIN' class='ASIAlist'> Singapore - Changi&nbsp;(SIN)</li><li style='display: none;' data-code='SJC' class='AMERICAlist'> San Jose - San Jose Municipal&nbsp;(SJC)</li><li style='display: none;' data-code='SJD' class='AMERICAlist'> San Jose del Cabo - Los Cabos International&nbsp;(SJD)</li><li style='display: list-item;' data-code='SJP' class='BRASILlist'>São José do Rio Preto&nbsp;(SJP)</li><li style='display: none;' data-code='SJU' class='AMERICAlist'> San Juan - Luis Munoz Marin&nbsp;(SJU)</li><li style='display: none;' data-code='SLA' class='AMERICAlist'> Salta - Martin M.De Guemes&nbsp;(SLA)</li><li style='display: none;' data-code='SLC' class='AMERICAlist'> Salt Lake City - Salt Lake City Internacional&nbsp;(SLC)</li><li style='display: list-item;' data-code='SLZ' class='BRASILlist'> São Luís - Marechal Cunha Machado&nbsp;(SLZ)</li><li style='display: none;' data-code='SMR' class='AMERICAlist'> Santa Marta - Simon Bolivar International&nbsp;(SMR)</li><li style='display: list-item;' data-code='SSA' class='BRASILlist'> Salvador - D.L.E.Magalhaes&nbsp;(SSA)</li><li style='display: none;' data-code='STL' class='AMERICAlist'> St Louis - Lambert&nbsp;(STL)</li><li style='display: list-item;' data-code='STM' class='BRASILlist'>Santarém&nbsp;(STM)</li><li style='display: none;' data-code='STO' class='EUROPlist'>Stockholm&nbsp;(STO)</li><li style='display: none;' data-code='STR' class='EUROPlist'> Stuttgart - Stuttgart Aeroporto&nbsp;(STR)</li><li style='display: none;' data-code='STT' class='AMERICAlist'> St Thomas Island - Cyril E. King&nbsp;(STT)</li><li style='display: none;' data-code='STX' class='AMERICAlist'> St Croix Island - Henry E Rohlsen&nbsp;(STX)</li><li style='display: none;' data-code='SVQ' class='EUROPlist'> Sevilla - Sevilla Aeroporto&nbsp;(SVQ)</li><li style='display: none;' data-code='SYD' class='ASIAlist'> Sydney - Kingsford Smith&nbsp;(SYD)</li><li style='display: none;' data-code='TBP' class='AMERICAlist'> Tumbes - Pedro Canga Rodriguez&nbsp;(TBP)</li><li style='display: none;' data-code='TCQ' class='AMERICAlist'> Tacna - C Ciriani Santa Rosa&nbsp;(TCQ)</li><li style='display: list-item;' data-code='THE' class='BRASILlist'> Teresina - Senador Petrônio Portella&nbsp;(THE)</li><li style='display: list-item;' data-code='TJL' class='BRASILlist'> Três Lagoas - Plinio Alarcom&nbsp;(TJL)</li><li style='display: none;' data-code='TLH' class='AMERICAlist'> Tallahassee - Tallahassee Municipal&nbsp;(TLH)</li><li style='display: none;' data-code='TPA' class='AMERICAlist'> Tampa - Tampa Internacional&nbsp;(TPA)</li><li style='display: none;' data-code='TPP' class='AMERICAlist'> Tarapoto - G Del Castillo Paredes&nbsp;(TPP)</li><li style='display: none;' data-code='TRU' class='AMERICAlist'> Trujillo - C Martinez De Pinillos&nbsp;(TRU)</li><li style='display: none;' data-code='TUC' class='AMERICAlist'> Tucuman - Benjamin Matienzo&nbsp;(TUC)</li><li style='display: none;' data-code='TUL' class='AMERICAlist'> Tulsa - Tulsa Internacional&nbsp;(TUL)</li><li style='display: none;' data-code='TXL' class='EUROPlist'> Berlin - Tegel&nbsp;(TXL)</li><li style='display: none;' data-code='TYO' class='ASIAlist'>Tokyo&nbsp;(TYO)</li><li style='display: none;' data-code='UAQ' class='AMERICAlist'> San Juan - Domingo F.Sarmiento&nbsp;(UAQ)</li><li style='display: list-item;' data-code='UBA' class='BRASILlist'> Uberaba - M.De Almeida Franco&nbsp;(UBA)</li><li style='display: list-item;' data-code='UDI' class='BRASILlist'> Uberlândia - César Bombonato&nbsp;(UDI)</li><li style='display: none;' data-code='UIB' class='AMERICAlist'> Quibdo - El Carano&nbsp;(UIB)</li><li style='display: none;' data-code='UIO' class='AMERICAlist'> Quito - Mariscal Sucre International&nbsp;(UIO)</li><li style='display: none;' data-code='USH' class='AMERICAlist'> Ushuaia - Malvinas Argentinas&nbsp;(USH)</li><li style='display: none;' data-code='VCE' class='EUROPlist'> Veneza - Marco Polo&nbsp;(VCE)</li><li style='display: list-item;' data-code='VCP' class='BRASILlist'> São Paulo - Campinas - Viracopos&nbsp;(VCP)</li><li style='display: list-item;' data-code='VDC' class='BRASILlist'> Vitoria Da Cnquis - P.Otacilio Figueiredo&nbsp;(VDC)</li><li style='display: none;' data-code='VER' class='AMERICAlist'> Veracruz - Heriberto Jara International&nbsp;(VER)</li><li style='display: none;' data-code='VGO' class='EUROPlist'> Vigo - Vigo Airport&nbsp;(VGO)</li><li style='display: none;' data-code='VIE' class='EUROPlist'> Vienna - Vienna Internacional&nbsp;(VIE)</li><li style='display: list-item;' data-code='VIX' class='BRASILlist'> Vitoria - E.De Aguiar Salles&nbsp;(VIX)</li><li style='display: none;' data-code='VUP' class='AMERICAlist'> Valledupar - Alfonso Lopez Pumarejo&nbsp;(VUP)</li><li style='display: none;' data-code='VVC' class='AMERICAlist'> Villavicencio - La Vanguardia&nbsp;(VVC)</li><li style='display: none;' data-code='VVI' class='AMERICAlist'> Santa Cruz - Viru Viru Internacional&nbsp;(VVI)</li><li style='display: none;' data-code='YUL' class='AMERICAlist'> Montreal - Pierre E. Trudeau Internacional&nbsp;(YUL)</li><li style='display: none;' data-code='YYZ' class='AMERICAlist'> Toronto - Lester B. Pearson Internacional&nbsp;(YYZ)</li><li style='display: none;' data-code='ZAL' class='AMERICAlist'> Valdivia - Pichoy&nbsp;(ZAL)</li><li style='display: none;' data-code='ZCO' class='AMERICAlist'> Temuco - La Araucania&nbsp;(ZCO)</li><li style='display: none;' data-code='ZOS' class='AMERICAlist'> Osorno - Canal Bajo&nbsp;(ZOS)</li><li style='display: none;' data-code='ZPC' class='AMERICAlist'>Pucon&nbsp;(ZPC)</li><li style='display: none;' data-code='ZRH' class='EUROPlist'> Zurich - Zurich Airport&nbsp;(ZRH)</li>";
	private Map<String, String> cityCodeMap;

	public SplitCityNameCode() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, String> splitExtractListCities() {
		Map<String, String> cityCodeMap = new HashMap<String, String>();

		String[] liList = destinationList.split("><");

		for (int i = 0; i < liList.length; i++) {
			String string = liList[i];

			if (string.contains("BRASILlist")) {
				String code = extractCode(string);
				String cityName = extractCityName(string);
				cityCodeMap.put(code, cityName);
			} else {//Destination out of Brasil

			}
		}
		return cityCodeMap;
	}
	
	private void printMapping(Map<String, String> cityCodeMap){
		Set<String> keySet = cityCodeMap.keySet();
		for(String key:keySet){
			System.out.println(key + "-" + cityCodeMap.get(key));
		}
	}
	
	private String extractCode(String listItem){
		int index = listItem.indexOf(DATA_CODE) + 1;
		String code = (String) listItem.subSequence(index + DATA_CODE.length(), index + DATA_CODE.length() + 3);
		
		return code;
	}
	
	private String extractCityName(String listItem){
		int nameIndex = listItem.indexOf(">") + 1; //jumps char '>'
		int nameIndexEnd = listItem.lastIndexOf("<") - 11; //removes unwanted escape character encoding
		String cityName = (String) listItem.subSequence(nameIndex, nameIndexEnd);
		while(cityName.charAt(0) == ' '){
			cityName = cityName.substring(1);
		}
		
		return cityName;
	}

}
