package bll.exporter;

public class CsvExporter extends Exporter{

	@Override
	protected WriterInterface createWriter() {
		
		return new CsvWriter();
	}
		
}
