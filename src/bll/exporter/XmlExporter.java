package bll.exporter;

public class XmlExporter extends Exporter{

	@Override
	protected WriterInterface createWriter() {
		
		return new XmlWriter();
		
	}

	
}
