import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class csvImport {
	Path fullpath;
	BufferedReader br = null;
	List<Score> ret = new ArrayList<Score>();
	Queries q = new Queries();

	public csvImport(String strfullpath) {
		this.fullpath = Paths.get(strfullpath);
		try {
			br = Files.newBufferedReader(fullpath);
			Charset.forName("UTF-8");
			String rl = "";

			while ((rl = br.readLine()) != null) {
				String parsedScore[] = rl.split(",");
				Score row = new Score(parsedScore[0].trim(), parsedScore[1].trim(), parsedScore[2].trim(),
						parsedScore[3].trim(), parsedScore[4].trim(), parsedScore[5].trim(), parsedScore[6].trim(),
						parsedScore[7].trim());
				ret.add(row);
				q.updateScoreRow(row);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Score> getChangedValue(){
		return ret;
	}
}
