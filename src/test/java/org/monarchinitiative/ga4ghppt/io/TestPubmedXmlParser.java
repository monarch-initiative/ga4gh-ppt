package org.monarchinitiative.ga4ghppt.io;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPubmedXmlParser {

    private static final String pubmedXml = """
             <PubmedArticle><MedlineCitation Status="MEDLINE" Owner="NLM" IndexingMethod="Curated">
             <PMID Version="1">37840311</PMID>
             <DateCompleted><Year>2024</Year><Month>01</Month><Day>26</Day></DateCompleted>
             <DateRevised><Year>2024</Year><Month>02</Month><Day>14</Day></DateRevised>
             <Article PubModel="Print-Electronic">
             <Journal>
             <ISSN IssnType="Electronic">2234-3814</ISSN>
             <JournalIssue CitedMedium="Internet"><Volume>44</Volume><Issue>3</Issue><PubDate><Year>2024</Year><Month>May</Month><Day>01</Day></PubDate></JournalIssue>
             <Title>Annals of laboratory medicine</Title><ISOAbbreviation>Ann Lab Med</ISOAbbreviation></Journal>
             <ArticleTitle>Re-evaluation of a Fibrillin-1 Gene Variant of Uncertain Significance Using the ClinGen Guidelines.</ArticleTitle>
             <Pagination><StartPage>271</StartPage><EndPage>278</EndPage><MedlinePgn>271-278</MedlinePgn></Pagination>
             <ELocationID EIdType="doi" ValidYN="Y">10.3343/alm.2023.0152</ELocationID>
             <Abstract><AbstractText Label="BACKGROUND" NlmCategory="UNASSIGNED">Marfan syndrome (MFS) is caused by fibrillin-1 gene (<i>FBN1</i>) variants.
             Mutational hotspots and/or well-established critical functional domains of <i>FBN1</i> include cysteine residues, calcium-binding consensus sequences,
             and amino acids related to interdomain packaging. Previous guidelines for variant interpretation do not reflect the features of genes or related diseases.
              Using the Clinical Genome Resource (ClinGen) <i>FBN1</i> variant curation expert panel (VCEP), we re-evaluated <i>FBN1</i> germline variants reported
              as variants of uncertain significance (VUSs).</AbstractText><AbstractText Label="METHODS" NlmCategory="UNASSIGNED">We re-evaluated 26 VUSs in
              <i>FBN1</i> reported in 161 patients with MFS. We checked the variants in the Human Genome Mutation Database, ClinVar, and VarSome databases
              and assessed their allele frequencies using the gnomAD database. Patients' clinical information was reviewed.</AbstractText>
              <AbstractText Label="RESULTS" NlmCategory="UNASSIGNED">Four missense variants affecting cysteines (c.460T&gt;C, c.1006T&gt;C, c.5330G&gt;C,
              and c.8020T&gt;C) were reclassified as likely pathogenic and were assigned PM1_strong or PM1. Two intronic variants were reclassified
              as benign by granting BA1 (stand-alone). Four missense variants were reclassified as likely benign. BP5 criteria were applied in cases
              with an alternate molecular basis for disease, one of which (c.7231G&gt;A) was discovered alongside a pathogenic <i>de novo</i> COL3A1
              variant (c.1988G&gt;T, p.Gly633Val).</AbstractText><AbstractText Label="CONCLUSIONS" NlmCategory="UNASSIGNED">Considering the high
              penetrance of <i>FBN1</i> variants and clinical variability of MFS, the detection of pathogenic variants is important. The ClinGen
              <i>FBN1</i> VCEP encompasses mutational hotspots and/or well-established critical functional domains and adjusts the criteria s
              pecifically for MFS; therefore, it is beneficial not only for identifying pathogenic <i>FBN1</i> variants but also for distinguishing
              these variants from those that cause other connective tissue disorders with overlapping clinical features.</AbstractText></Abstract>
              <AuthorList CompleteYN="Y"><Author ValidYN="Y"><LastName>Kim</LastName><ForeName>Seo Wan</ForeName><Initials>SW</Initials>
              <Identifier Source="ORCID">0000-0003-0390-9191</Identifier><AffiliationInfo><Affiliation>Department of Laboratory Medicine, Severance Hospital, Yonsei University College of Medicine, Seoul, Korea.</Affiliation></AffiliationInfo></Author>
              <Author ValidYN="Y"><LastName>Kim</LastName><ForeName>Boyeon</ForeName><Initials>B</Initials><Identifier Source="ORCID">0000-0003-1867-8648</Identifier>
              <AffiliationInfo><Affiliation>Department of Laboratory Medicine, Gangnam Severance Hospital, Yonsei University College of Medicine, Seoul, Korea.</Affiliation>
              </AffiliationInfo></Author><Author ValidYN="Y"><LastName>Kim</LastName><ForeName>Yoonjung</ForeName><Initials>Y</Initials>
              <Identifier Source="ORCID">0000-0002-4370-4265</Identifier><AffiliationInfo><Affiliation>Department of Laboratory Medicine, Gangnam Severance Hospital, Yonsei University College of Medicine, Seoul, Korea.</Affiliation></AffiliationInfo></Author><Author ValidYN="Y"><LastName>Lee</LastName><ForeName>Kyung-A</ForeName><Initials>KA</Initials><Identifier Source="ORCID">0000-0001-5320-6705</Identifier><AffiliationInfo><Affiliation>Department of Laboratory Medicine, Gangnam Severance Hospital, Yonsei University College of Medicine, Seoul, Korea.</Affiliation></AffiliationInfo></Author></AuthorList><Language>eng</Language><PublicationTypeList><PublicationType UI="D016428">Journal Article</PublicationType></PublicationTypeList><ArticleDate DateType="Electronic"><Year>2023</Year><Month>10</Month><Day>16</Day></ArticleDate></Article><MedlineJournalInfo><Country>Korea (South)</Country><MedlineTA>Ann Lab Med</MedlineTA><NlmUniqueID>101571172</NlmUniqueID><ISSNLinking>2234-3806</ISSNLinking></MedlineJournalInfo>
              <ChemicalList><Chemical><RegistryNumber>0</RegistryNumber><NameOfSubstance UI="D000071838">Fibrillin-1</NameOfSubstance></Chemical>
              <Chemical><RegistryNumber>K848JZ4886</RegistryNumber><NameOfSubstance UI="D003545">Cysteine</NameOfSubstance></Chemical></ChemicalList>
              <CitationSubset>IM</CitationSubset><MeshHeadingList><MeshHeading><DescriptorName UI="D006801" MajorTopicYN="N">Humans</DescriptorName></MeshHeading>
              <MeshHeading><DescriptorName UI="D000071838" MajorTopicYN="N">Fibrillin-1</DescriptorName><QualifierName UI="Q000235" MajorTopicYN="N">genetics</QualifierName></MeshHeading>
              <MeshHeading><DescriptorName UI="D009154" MajorTopicYN="N">Mutation</DescriptorName></MeshHeading>
              <MeshHeading><DescriptorName UI="D008382" MajorTopicYN="Y">Marfan Syndrome</DescriptorName><QualifierName UI="Q000175" MajorTopicYN="N">diagnosis</QualifierName><QualifierName UI="Q000235" MajorTopicYN="N">genetics</QualifierName><QualifierName UI="Q000473" MajorTopicYN="N">pathology</QualifierName></MeshHeading>
              <MeshHeading><DescriptorName UI="D020125" MajorTopicYN="N">Mutation, Missense</DescriptorName></MeshHeading>
              <MeshHeading><DescriptorName UI="D005787" MajorTopicYN="N">Gene Frequency</DescriptorName></MeshHeading>
              <MeshHeading><DescriptorName UI="D003545" MajorTopicYN="N">Cysteine</DescriptorName><QualifierName UI="Q000235" MajorTopicYN="N">genetics</QualifierName></MeshHeading>
              </MeshHeadingList>
              <KeywordList Owner="NOTNLM"><Keyword MajorTopicYN="N">ClinGen</Keyword><Keyword MajorTopicYN="N">Connective tissue</Keyword>
              <Keyword MajorTopicYN="N">Fibrillin-1</Keyword><Keyword MajorTopicYN="N">Gene frequency</Keyword><Keyword MajorTopicYN="N">Marfan syndrome</Keyword>
              <Keyword MajorTopicYN="N">Penetrance</Keyword></KeywordList>
              <CoiStatement><b>CONFLICTS OF INTEREST</b>. None declared.</CoiStatement>
              </MedlineCitation><PubmedData><History><PubMedPubDate PubStatus="received"><Year>2023</Year><Month>4</Month><Day>10</Day></PubMedPubDate>
              <PubMedPubDate PubStatus="revised"><Year>2023</Year><Month>7</Month><Day>25</Day></PubMedPubDate>
              <PubMedPubDate PubStatus="accepted"><Year>2023</Year><Month>9</Month><Day>12</Day></PubMedPubDate>
              <PubMedPubDate PubStatus="medline"><Year>2024</Year><Month>1</Month><Day>26</Day><Hour>6</Hour><Minute>43</Minute></PubMedPubDate>
              <PubMedPubDate PubStatus="pubmed"><Year>2023</Year><Month>10</Month><Day>16</Day><Hour>6</Hour><Minute>48</Minute></PubMedPubDate>
              <PubMedPubDate PubStatus="entrez"><Year>2023</Year><Month>10</Month><Day>16</Day><Hour>3</Hour><Minute>43</Minute></PubMedPubDate>
              </History>
              <PublicationStatus>ppublish</PublicationStatus>
              <ArticleIdList><ArticleId IdType="pubmed">37840311</ArticleId><ArticleId IdType="pmc">PMC10813823</ArticleId><ArticleId IdType="doi">10.3343/alm.2023.0152</ArticleId><ArticleId IdType="pii">alm.2023.0152</ArticleId></ArticleIdList><ReferenceList><Reference><Citation>Dean JC. Marfan syndrome: clinical diagnosis and management. Eur J Hum Genet. 2007;15:724&#x2013;33. doi: 10.1038/sj.ejhg.5201851.</Citation><ArticleIdList><ArticleId IdType="doi">10.1038/sj.ejhg.5201851</ArticleId><ArticleId IdType="pubmed">17487218</ArticleId></ArticleIdList></Reference><Reference><Citation>Sakai LY, Keene DR, Engvall E. Fibrillin, a new 350-kD glycoprotein, is a component of extracellular microfibrils. J Cell Biol. 1986;103:2499&#x2013;509. doi: 10.1083/jcb.103.6.2499.</Citation><ArticleIdList><ArticleId IdType="doi">10.1083/jcb.103.6.2499</ArticleId><ArticleId IdType="pmc">PMC2114568</ArticleId><ArticleId IdType="pubmed">3536967</ArticleId></ArticleIdList></Reference><Reference><Citation>Schrenk S, Cenzi C, Bertalot T, Conconi MT, Di Liddo R. Structural and functional failure of fibrillin-1 in human diseases (Review) Int J Mol Med. 2018;41:1213&#x2013;23. doi: 10.3892/ijmm.2017.3343.</Citation><ArticleIdList><ArticleId IdType="doi">10.3892/ijmm.2017.3343</ArticleId><ArticleId IdType="pubmed">29286095</ArticleId></ArticleIdList></Reference><Reference><Citation>Vollbrandt T, Tiedemann K, El-Hallous E, Lin G, Brinckmann J, John H, et al. Consequences of cysteine mutations in calcium-binding epidermal growth factor modules of fibrillin-1. J Biol Chem. 2004;279:32924&#x2013;31. doi: 10.1074/jbc.M405239200.</Citation><ArticleIdList><ArticleId IdType="doi">10.1074/jbc.M405239200</ArticleId><ArticleId IdType="pubmed">15161917</ArticleId></ArticleIdList></Reference><Reference><Citation>Zhang M, Chen Z, Chen T, Sun X, Jiang Y. Cysteine substitution and calcium-binding mutations in FBN1 cbEGF-like domains are associated with severe ocular involvement in patients with congenital ectopia lentis. Front Cell Dev Biol. 2021;9:816397. doi: 10.3389/fcell.2021.816397.</Citation><ArticleIdList><ArticleId IdType="doi">10.3389/fcell.2021.816397</ArticleId><ArticleId IdType="pmc">PMC8882981</ArticleId><ArticleId IdType="pubmed">35237611</ArticleId></ArticleIdList></Reference><Reference><Citation>Baudhuin LM, Kluge ML, Kotzer KE, Lagerstedt SA. Variability in gene-based knowledge impacts variant classification: an analysis of FBN1 missense variants in ClinVar. Eur J Hum Genet. 2019;27:1550&#x2013;60. doi: 10.1038/s41431-019-0440-3.</Citation><ArticleIdList><ArticleId IdType="doi">10.1038/s41431-019-0440-3</ArticleId><ArticleId IdType="pmc">PMC6777626</ArticleId><ArticleId IdType="pubmed">31227806</ArticleId></ArticleIdList></Reference><Reference><Citation>Rivera-Mu&#xf1;oz EA, Milko LV, Harrison SM, Azzariti DR, Kurtz CL, Lee K, et al. ClinGen Variant Curation Expert Panel experiences and standardized processes for disease and gene-level specification of the ACMG/AMP guidelines for sequence variant interpretation. Hum Mutat. 2018;39:1614&#x2013;22. doi: 10.1002/humu.23645.</Citation><ArticleIdList><ArticleId IdType="doi">10.1002/humu.23645</ArticleId><ArticleId IdType="pmc">PMC6225902</ArticleId><ArticleId IdType="pubmed">30311389</ArticleId></ArticleIdList></Reference><Reference><Citation>De Backer J. ClinGen FBN1 Expert Panel Specifications to the ACMG/AMP Variant Interpretation Guidelines Version 1.  [Updated on Feb 2022].
                https://clinicalgenome.org/docs/application-for-variant-curation-expert-panel-status.\s
            </Citation></Reference><Reference><Citation>Ioannidis NM, Rothstein JH, Pejaver V, Middha S, McDonnell SK, Baheti S, et al. REVEL: an ensemble method for predicting the pathogenicity of rare missense variants. Am J Hum Genet. 2016;99:877&#x2013;85. doi: 10.1016/j.ajhg.2016.08.016.</Citation><ArticleIdList><ArticleId IdType="doi">10.1016/j.ajhg.2016.08.016</ArticleId><ArticleId IdType="pmc">PMC5065685</ArticleId><ArticleId IdType="pubmed">27666373</ArticleId></ArticleIdList></Reference><Reference><Citation>Chen ZR, Bao MH, Wang XY, Yang YM, Huang B, Han ZL, et al. Genetic variants in Chinese patients with sporadic Stanford type A aortic dissection. J Thorac Dis. 2021;13:4008&#x2013;22. doi: 10.21037/jtd-20-2758.</Citation><ArticleIdList><ArticleId IdType="doi">10.21037/jtd-20-2758</ArticleId><ArticleId IdType="pmc">PMC8339749</ArticleId><ArticleId IdType="pubmed">34422331</ArticleId></ArticleIdList></Reference><Reference><Citation>Takeda N, Inuzuka R, Maemura S, Morita H, Nawata K, Fujita D, et al. Impact of pathogenic FBN1 variant types on the progression of aortic disease in patients with Marfan syndrome. Circ Genom Precis Med. 2018;11:e002058. doi: 10.1161/CIRCGEN.118.002321.</Citation><ArticleIdList><ArticleId IdType="doi">10.1161/CIRCGEN.118.002321</ArticleId><ArticleId IdType="pubmed">29848614</ArticleId></ArticleIdList></Reference><Reference><Citation>Faivre L, Collod-Beroud G, Loeys BL, Child A, Binquet C, Gautier E, et al. Effect of mutation type and location on clinical outcome in 1,013 probands with Marfan syndrome or related phenotypes and FBN1 mutations: an international study. Am J Hum Genet. 2007;81:454&#x2013;66. doi: 10.1086/520125.</Citation><ArticleIdList><ArticleId IdType="doi">10.1086/520125</ArticleId><ArticleId IdType="pmc">PMC1950837</ArticleId><ArticleId IdType="pubmed">17701892</ArticleId></ArticleIdList></Reference><Reference><Citation>Pisano C, Balistreri CR, Nardi P, Altieri C, Bertoldo F, Buioni D, et al. Risk of aortic dissection in patients with ascending aorta aneurysm: a new biological, morphological, and biomechanical network behind the aortic diameter. Vessel Plus. 2020;4:33. doi: 10.20517/2574-1209.2020.21.</Citation><ArticleIdList><ArticleId IdType="doi">10.20517/2574-1209.2020.21</ArticleId></ArticleIdList></Reference><Reference><Citation>Biggin A, Holman K, Brett M, Bennetts B, Ad&#xe8;s L. Detection of thirty novel FBN1 mutations in patients with Marfan syndrome or a related fibrillinopathy. Hum Mutat. 2004;23:99. doi: 10.1002/humu.9207.</Citation><ArticleIdList><ArticleId IdType="doi">10.1002/humu.9207</ArticleId><ArticleId IdType="pubmed">14695540</ArticleId></ArticleIdList></Reference><Reference><Citation>Comeglio P, Johnson P, Arno G, Brice G, Evans A, Aragon-Martin J, et al. The importance of mutation detection in Marfan syndrome and Marfan-related disorders: report of 193 FBN1 mutations. Hum Mutat. 2007;28:928. doi: 10.1002/humu.9505.</Citation><ArticleIdList><ArticleId IdType="doi">10.1002/humu.9505</ArticleId><ArticleId IdType="pubmed">17657824</ArticleId></ArticleIdList></Reference><Reference><Citation>Jalkh N, Mehawej C, Chouery E. Actionable exomic secondary findings in 280 Lebanese participants. Front Genet. 2020;11:208. doi: 10.3389/fgene.2020.00208.</Citation><ArticleIdList><ArticleId IdType="doi">10.3389/fgene.2020.00208</ArticleId><ArticleId IdType="pmc">PMC7083077</ArticleId><ArticleId IdType="pubmed">32231684</ArticleId></ArticleIdList></Reference><Reference><Citation>Regalado ES, Guo DC, Prakash S, Bensend TA, Flynn K, Estrera A, et al. Aortic disease presentation and outcome associated with ACTA2 mutations. Circ Cardiovasc Genet. 2015;8:457&#x2013;64. doi: 10.1161/CIRCGENETICS.114.000943.</Citation><ArticleIdList><ArticleId IdType="doi">10.1161/CIRCGENETICS.114.000943</ArticleId><ArticleId IdType="pmc">PMC4601641</ArticleId><ArticleId IdType="pubmed">25759435</ArticleId></ArticleIdList></Reference><Reference><Citation>Diness BR, Palmquist RN, Norling R, Hove H, Bundgaard H, Hertz JM, et al. Expanding the cerebrovascular phenotype of the p.R258H variant in ACTA2 related hereditary thoracic aortic disease (HTAD) J Neurol Sci. 2020;415:116897. doi: 10.1016/j.jns.2020.116897.</Citation><ArticleIdList><ArticleId IdType="doi">10.1016/j.jns.2020.116897</ArticleId><ArticleId IdType="pubmed">32464348</ArticleId></ArticleIdList></Reference><Reference><Citation>Kathiravel U, Keyser B, Hoffjan S, K&#xf6;tting J, M&#xfc;ller M, Sivalingam S, et al. High-density oligonucleotide-based resequencing assay for mutations causing syndromic and non-syndromic forms of thoracic aortic aneurysms and dissections. Mol Cell Probes. 2013;27:103&#x2013;8. doi: 10.1016/j.mcp.2012.10.002.</Citation><ArticleIdList><ArticleId IdType="doi">10.1016/j.mcp.2012.10.002</ArticleId><ArticleId IdType="pubmed">23142374</ArticleId></ArticleIdList></Reference><Reference><Citation>Yang H, Luo M, Fu Y, Cao Y, Yin K, Li W, et al. Genetic testing of 248 Chinese aortopathy patients using a panel assay. Sci Rep. 2016;6:33002. doi: 10.1038/srep33002.</Citation><ArticleIdList><ArticleId IdType="doi">10.1038/srep33002</ArticleId><ArticleId IdType="pmc">PMC5017237</ArticleId><ArticleId IdType="pubmed">27611364</ArticleId></ArticleIdList></Reference><Reference><Citation>Milewicz DM, &#xd8;stergaard JR, Ala-Kokko LM, Khan N, Grange DK, Mendoza-Londono R, et al. De novo ACTA2 mutation causes a novel syndrome of multisystemic smooth muscle dysfunction. Am J Med Genet A. 2010;152A:2437&#x2013;43. doi: 10.1002/ajmg.a.33657.</Citation><ArticleIdList><ArticleId IdType="doi">10.1002/ajmg.a.33657</ArticleId><ArticleId IdType="pmc">PMC3573757</ArticleId><ArticleId IdType="pubmed">20734336</ArticleId></ArticleIdList></Reference><Reference><Citation>Guo DC, Papke CL, Tran-Fadulu V, Regalado ES, Avidan N, Johnson RJ, et al. Mutations in smooth muscle alpha-actin (ACTA2) cause coronary artery disease, stroke, and Moyamoya disease, along with thoracic aortic disease. Am J Hum Genet. 2009;84:617&#x2013;27. doi: 10.1016/j.ajhg.2009.04.007.</Citation><ArticleIdList><ArticleId IdType="doi">10.1016/j.ajhg.2009.04.007</ArticleId><ArticleId IdType="pmc">PMC2680995</ArticleId><ArticleId IdType="pubmed">19409525</ArticleId></ArticleIdList></Reference><Reference><Citation>Groth KA, Von Kodolitsch Y, Kutsche K, Gaustadnes M, Thorsen K, Andersen NH, et al. Evaluating the quality of Marfan genotype-phenotype correlations in existing FBN1 databases. Genet Med. 2017;19:772&#x2013;7. doi: 10.1038/gim.2016.181.</Citation><ArticleIdList><ArticleId IdType="doi">10.1038/gim.2016.181</ArticleId><ArticleId IdType="pubmed">27906200</ArticleId></ArticleIdList></Reference><Reference><Citation>Malfait F, Francomano C, Byers P, Belmont J, Berglund B, Black J, et al. The 2017 international classification of the Ehlers-Danlos syndromes. Am J Med Genet C Semin Med Genet. 2017;175:8&#x2013;26. doi: 10.1002/ajmg.c.31552.</Citation><ArticleIdList><ArticleId IdType="doi">10.1002/ajmg.c.31552</ArticleId><ArticleId IdType="pubmed">28306229</ArticleId></ArticleIdList></Reference><Reference><Citation>Yen JL, Lin SP, Chen MR, Niu DM. Clinical features of Ehlers-Danlos syndrome. J Formos Med Assoc. 2006;105:475&#x2013;80. doi: 10.1016/S0929-6646(09)60187-X.</Citation><ArticleIdList><ArticleId IdType="doi">10.1016/S0929-6646(09)60187-X</ArticleId><ArticleId IdType="pubmed">16801035</ArticleId></ArticleIdList></Reference><Reference><Citation>Clapp IM, Paul KM, Beck EC, Nho SJ. Hypermobile disorders and their effects on the hip joint. Front Surg. 2021;8:596971. doi: 10.3389/fsurg.2021.596971.</Citation><ArticleIdList><ArticleId IdType="doi">10.3389/fsurg.2021.596971</ArticleId><ArticleId IdType="pmc">PMC8027473</ArticleId><ArticleId IdType="pubmed">33842528</ArticleId></ArticleIdList></Reference><Reference><Citation>Pepin MG, Schwarze U, Rice KM, Liu M, Leistritz D, Byers PH. Survival is affected by mutation type and molecular mechanism in vascular Ehlers-Danlos syndrome (EDS type IV) Genet Med. 2014;16:881&#x2013;8. doi: 10.1038/gim.2014.72.</Citation><ArticleIdList><ArticleId IdType="doi">10.1038/gim.2014.72</ArticleId><ArticleId IdType="pubmed">24922459</ArticleId></ArticleIdList></Reference><Reference><Citation>Pepin MG, Murray ML, Bailey S, Leistritz-Kessler D, Schwarze U, Byers PH. The challenge of comprehensive and consistent sequence variant interpretation between clinical laboratories. Genet Med. 2016;18:20&#x2013;4. doi: 10.1038/gim.2015.31.</Citation><ArticleIdList><ArticleId IdType="doi">10.1038/gim.2015.31</ArticleId><ArticleId IdType="pubmed">25834947</ArticleId></ArticleIdList></Reference><Reference><Citation>Najafi A, Caspar SM, Meienberg J, Rohrbach M, Steinmann B, Matyas G. Variant filtering, digenic variants, and other challenges in clinical sequencing: a lesson from fibrillinopathies. Clin Genet. 2020;97:235&#x2013;45. doi: 10.1111/cge.13640.</Citation><ArticleIdList><ArticleId IdType="doi">10.1111/cge.13640</ArticleId><ArticleId IdType="pmc">PMC7004123</ArticleId><ArticleId IdType="pubmed">31506931</ArticleId></ArticleIdList></Reference><Reference><Citation>Hayward C, Porteous ME, Brock DJ. Mutation screening of all 65 exons of the fibrillin-1 gene in 60 patients with Marfan syndrome: report of 12 novel mutations. Hum Mutat. 1997;10:280&#x2013;9. doi: 10.1002/(SICI)1098-1004(1997)10:4&lt;280::AID-HUMU3&gt;3.0.CO;2-L.</Citation><ArticleIdList><ArticleId IdType="doi">10.1002/(SICI)1098-1004(1997)10:4&lt;280::AID-HUMU3&gt;3.0.CO;2-L</ArticleId><ArticleId IdType="pubmed">9338581</ArticleId></ArticleIdList></Reference><Reference><Citation>Yang RQ, Jabbari J, Cheng XS, Jabbari R, Nielsen JB, Risgaard B, et al. New population-based exome data question the pathogenicity of some genetic variants previously associated with Marfan syndrome. BMC Genet. 2014;15:74. doi: 10.1186/1471-2156-15-74.</Citation><ArticleIdList><ArticleId IdType="doi">10.1186/1471-2156-15-74</ArticleId><ArticleId IdType="pmc">PMC4070351</ArticleId><ArticleId IdType="pubmed">24941995</ArticleId></ArticleIdList></Reference><Reference><Citation>Groth KA, Gaustadnes M, Thorsen K, &#xd8;stergaard JR, Jensen UB, Gravholt CH, et al. Difficulties in diagnosing Marfan syndrome using current FBN1 databases. Genet Med. 2016;18:98&#x2013;102. doi: 10.1038/gim.2015.32.</Citation><ArticleIdList><ArticleId IdType="doi">10.1038/gim.2015.32</ArticleId><ArticleId IdType="pubmed">25812041</ArticleId></ArticleIdList></Reference><Reference><Citation>Overwater E, Marsili L, Baars MJH, Baas AF, van de Beek I, Dulfer E, et al. Results of next-generation sequencing gene panel diagnostics including copy-number variation analysis in 810 patients suspected of heritable thoracic aortic disorders. Hum Mutat. 2018;39:1173&#x2013;92. doi: 10.1002/humu.23565.</Citation><ArticleIdList><ArticleId IdType="doi">10.1002/humu.23565</ArticleId><ArticleId IdType="pmc">PMC6175145</ArticleId><ArticleId IdType="pubmed">29907982</ArticleId></ArticleIdList></Reference><Reference><Citation>Na R, Hong J, Gu H, Lee W, Lee JL, Chun S, et al. RNA Sequencing Provides Evidence for Pathogenicity of a Novel CHEK2 Splice Variant (C.1009-7T&gt;G) Ann Lab Med. 2022;42:380&#x2013;3. doi: 10.3343/alm.2022.42.3.380.</Citation><ArticleIdList><ArticleId IdType="doi">10.3343/alm.2022.42.3.380</ArticleId><ArticleId IdType="pmc">PMC8677473</ArticleId><ArticleId IdType="pubmed">34907112</ArticleId></ArticleIdList></Reference><Reference><Citation>Park SY, Lee JM, Kim MJ, Chung NG, Lee JB, Kim Y, et al. Validation of Pathogenicity of Gene Variants in Fanconi Anemia Using Patient-derived Dermal Fibroblasts. Ann Lab Med. 2023;43:127&#x2013;31. doi: 10.3343/alm.2023.43.1.127.</Citation><ArticleIdList><ArticleId IdType="doi">10.3343/alm.2023.43.1.127</ArticleId><ArticleId IdType="pmc">PMC9467830</ArticleId><ArticleId IdType="pubmed">36045072</ArticleId></ArticleIdList></Reference><Reference><Citation>Gu H, Hong J, Lee W, Kim SB, Chun S, Min WK. RNA Sequencing for Elucidating an Intronic Variant of Uncertain Significance (SDHD c.314+ 3A&gt;T) in Splicing Site Consensus Sequences. Ann Lab Med. 2022;42:376&#x2013;9. doi: 10.3343/alm.2022.42.3.376.</Citation><ArticleIdList><ArticleId IdType="doi">10.3343/alm.2022.42.3.376</ArticleId><ArticleId IdType="pmc">PMC8677484</ArticleId><ArticleId IdType="pubmed">34907111</ArticleId></ArticleIdList></Reference><Reference><Citation>Meester JAN, Verstraeten A, Schepers D, Alaerts M, Van Laer L, Loeys BL. Differences in manifestations of Marfan syndrome, Ehlers-Danlos syndrome, and Loeys-Dietz syndrome. Ann Cardiothorac Surg. 2017;6:582&#x2013;94. doi: 10.21037/acs.2017.11.03.</Citation><ArticleIdList><ArticleId IdType="doi">10.21037/acs.2017.11.03</ArticleId><ArticleId IdType="pmc">PMC5721110</ArticleId>
            <ArticleId IdType="pubmed">29270370</ArticleId></ArticleIdList></Reference></ReferenceList></PubmedData></PubmedArticle>
            """;

    @Test
    public void testGetPmid() {
        PubmedXmlParser parser = new PubmedXmlParser(pubmedXml);
        String expected = "Re-evaluation of a Fibrillin-1 Gene Variant of Uncertain Significance Using the ClinGen Guidelines.";
        Optional<String> opt = parser.getTitle();
        assertTrue(opt.isPresent());
        assertEquals(expected, opt.get());
        opt = parser.getPmid();
        String expectedPmid = "PMID:37840311";
        assertTrue(opt.isPresent());
        assertEquals(expectedPmid, opt.get());
    }

}
