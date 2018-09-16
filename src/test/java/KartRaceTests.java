import com.gympass.kart.Main;
import com.gympass.kart.impl.KartLogDecoder;
import com.gympass.kart.impl.RaceResultManager;
import com.gympass.kart.model.KartLog;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author michael
 */
public class KartRaceTests {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void notValidProgramParamsThrowNotFileFoundExeption1() throws FileNotFoundException {
        exception.expect(FileNotFoundException.class);
        String[] args = {};
        Main.validateFileInput(args);
    }

    @Test
    public void notValidProgramParamsThrowNotFileFoundExeption2() throws FileNotFoundException {
        exception.expect(FileNotFoundException.class);
        String[] args = {"notvalid"};
        Main.validateFileInput(args);
    }

    @Test
    public void notValidProgramParamsThrowNotFileFoundExeption3() throws FileNotFoundException {
        exception.expect(FileNotFoundException.class);
        String[] args = {"notvalid1", "notvalid2"};
        Main.validateFileInput(args);
    }

    @Test
    public void validProgramParamsNotThrowNotFileFoundExeption() throws FileNotFoundException {
        exception.reportMissingExceptionWithMessage("Not exceptions here!");
        String[] args = {"src/test/resources/kartlog_valid", "other"};
        Main.validateFileInput(args);
    }

    @Test
    public void kartLogFileWithDiferentsStringsLenghtBetweenColumnsShoudBeFine() throws Exception {
        exception.reportMissingExceptionWithMessage("Not exceptions here!");
        KartLogDecoder kartLogDecoder = new KartLogDecoder();
        kartLogDecoder
            .decode(new File("src/test/resources/kartlog_valid_different_string_lenght_columns"));
    }

    @Test
    public void kartLogFileWithDiferentColumnsSizeThrowException() throws Exception {
        exception.expectMessage("File input with Wrong number of columns!");
        KartLogDecoder kartLogDecoder = new KartLogDecoder();
        kartLogDecoder
            .decode(new File("src/test/resources/kartlog_not_valid_wrong_number_columns"));
    }

    @Test
    public void pilotNameWithSpecialCharactersThrowExection() throws Exception {
        exception.expect(Exception.class);
        KartLogDecoder kartLogDecoder = new KartLogDecoder();
        kartLogDecoder.getPilotName("pil@t");
    }

    @Test
    public void pilotNameWithNumbresThrowExection() throws Exception {
        exception.expect(Exception.class);
        KartLogDecoder kartLogDecoder = new KartLogDecoder();
        kartLogDecoder.getPilotName("p1lot");
    }

    @Test
    public void pilotCodeWithSpecialCharactersThrowExection() throws Exception {
        exception.expect(Exception.class);
        KartLogDecoder kartLogDecoder = new KartLogDecoder();
        kartLogDecoder.getPilotCode("001@");
    }

    @Test
    public void MalformedLocalTimeThrowExection() {
        exception.expect(Exception.class);
        KartLogDecoder kartLogDecoder = new KartLogDecoder();
        kartLogDecoder.getLocalTime("23:49:11.075-a", "HH:mm:ss.SSS");
    }


    @Test
    public void pilotBestLap() throws Exception {
        ArrayList<KartLog> kartLogs = new ArrayList();
        RaceResultManager raceResultManager = new RaceResultManager();
        KartLogDecoder kartLogDecoder = new KartLogDecoder();

        String line = "23:54:57.757      011 – S.VETTEL                          3\t\t1:18.097\t\t\t                  35,633";
        String[] columns = line.split("\\s+");

        KartLog kartLog = new KartLog();
        LocalTime ckeckTime = kartLogDecoder.getLocalTime(columns[0], "HH:mm:ss.SSS");
        kartLog.setCheckTime(ckeckTime);
        kartLog.setPilotCode(kartLogDecoder.getPilotCode(columns[1]));
        kartLog.setPilotName(kartLogDecoder.getPilotName(columns[3]));
        kartLog.setLapNumber(Integer.valueOf(columns[4]));
        Duration duration = kartLogDecoder.getDuration(columns[5]);
        kartLog.setLapDuration(duration);
        kartLog.setMeanLapSpeed(Float.valueOf(columns[6].replace(",", ".")));
        //add kartlog_valid log to the unordered list
        kartLogs.add(kartLog);
        Assert.assertEquals(raceResultManager.findBestLap(kartLogs).getPilotCode(), "011");

    }

    @Test
    public void pilotRaceDuration() throws Exception {
        ArrayList<KartLog> kartLogs = new ArrayList();
        RaceResultManager raceResultManager = new RaceResultManager();
        KartLogDecoder kartLogDecoder = new KartLogDecoder();

        String line = "23:54:57.757      011 – S.VETTEL                          3\t\t1:18.097\t\t\t                  35,633";
        String[] columns = line.split("\\s+");

        KartLog kartLog = new KartLog();
        LocalTime ckeckTime = kartLogDecoder.getLocalTime(columns[0], "HH:mm:ss.SSS");
        kartLog.setCheckTime(ckeckTime);
        kartLog.setPilotCode(kartLogDecoder.getPilotCode(columns[1]));
        kartLog.setPilotName(kartLogDecoder.getPilotName(columns[3]));
        kartLog.setLapNumber(Integer.valueOf(columns[4]));
        Duration duration = kartLogDecoder.getDuration(columns[5]);
        kartLog.setLapDuration(duration);
        kartLog.setMeanLapSpeed(Float.valueOf(columns[6].replace(",", ".")));
        //add kartlog_valid log to the unordered list
        kartLogs.add(kartLog);
        Assert.assertEquals(raceResultManager.getPilotRaceDuration(kartLogs),
            kartLog.getLapDuration());

    }


}
