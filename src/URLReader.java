import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class URLReader {

    public ArrayList<String > readUrl(String aUrl) {
        ArrayList<String> lst = new ArrayList<>();
        try {
            URL url = new URL(aUrl);
            try {
                var inread = new InputStreamReader(url.openStream());
                BufferedReader bufr = new BufferedReader(inread);
                String inputLine = bufr.readLine();
                while (inputLine != null) {
                    lst.add(inputLine);
                    inputLine = bufr.readLine();
                }
            } catch (IOException ioerr) {
                System.out.println("Fejl i læsning=" + ioerr.getMessage());
            }
        } catch (MalformedURLException malerr) {
            System.out.println("Fejl i URL=" + malerr.getMessage());
        }
        return lst;
    }
}
