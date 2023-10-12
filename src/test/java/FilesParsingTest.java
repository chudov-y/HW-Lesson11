import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import model.CarsFactoryModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FilesParsingTest {
    ClassLoader cl = FilesParsingTest.class.getClassLoader();

    @Test
    public void zipTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("cars.zip");
            ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                System.out.printf("File name: %s \n", name);
                if (entry.getName().contains("cars-factory.csv")){

                        CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                        List<String[]> content = csvReader.readAll();
                        Assertions.assertEquals(3, content.size());

                        final String[] firstRow = content.get(0);
                        Assertions.assertArrayEquals(new String[]{"BMW", "Германия"}, firstRow);

                        System.out.println("CSV File");
                    }

                if (entry.getName().contains("cars-factory.pdf")){
                    PDF pdf = new PDF(zis);

                    Assertions.assertTrue(pdf.text.contains("Производство машин"));

                    System.out.println("PDF File");
                }
                if (entry.getName().contains("cars-factory.xlsx")){
                    XLS xls = new XLS(zis);
                    Assertions.assertEquals("Германия",
                            xls.excel.getSheetAt(0)
                                    .getRow(1)
                                    .getCell(1)
                                    .getStringCellValue());
                    System.out.println("XLS File");
                }
                }
            }
   }

    @Test
    void jsonTest() throws  Exception {
        ObjectMapper mapper = new ObjectMapper();

        try (InputStream stream = cl.getResourceAsStream("cars-factory.json");
             Reader reader = new InputStreamReader(stream)) {
             CarsFactoryModel carsFactory = mapper.readValue(reader, CarsFactoryModel.class);

           Assertions.assertEquals("Германия", carsFactory.getCountry());
           Assertions.assertTrue(carsFactory.getCarBrand().contains("BMW"));
           Assertions.assertEquals(137, carsFactory.getCarAmount());
        }
    }
}

