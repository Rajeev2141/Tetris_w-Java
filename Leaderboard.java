/*
 * This class is resposible for keeping record of the highest scores
 * Rajeev Patel
 * 25 April 2024
 */

import java.io.*;
import java.util.*;
import javax.swing.*;

public class Leaderboard {
 
    private File data = new File("./scores.txt");
    private ArrayList<String> names = new ArrayList<String>();
    private ArrayList<Integer> scores = new ArrayList<Integer>();

    public Leaderboard() {

        readFile();
    }

    public void showLeaderboard()
    {
        String output = "";
        for(int index = 0; index < scores.size(); index++)
        {
            output += names.get(index) + ": " + scores.get(index) + "\n";
        }
        JOptionPane.showMessageDialog(null, output, 
            "HighScores", 1);
    }

    private void readFile()
    {

        try {
            Scanner inScan = new Scanner(data);
            inScan.useDelimiter(":|\n");
            while(inScan.hasNext())
            {
                names.add(inScan.next());
                scores.add(Integer.parseInt(inScan.next()));
            }
            inScan.close();
        }catch(IOException ioe) {}
        sortScores();
    }

    private void sortScores()
    {

        int[] oldScoresPos = new int[scores.size()];
        String[] oldNamePos = new String[names.size()];

        for(int index = 0; index < scores.size(); index++)
        {
            oldScoresPos[index] = scores.get(index);
            oldNamePos[index] = names.get(index);
        }

        Collections.sort(scores, Collections.reverseOrder());
        for(int pos = 0; pos < oldNamePos.length; pos++)
        {
            int score = oldScoresPos[pos];
            int newIndex = scores.indexOf(score);
            names.set(newIndex, oldNamePos[pos]);
        }
    }

    public void clear()
    {
        try{
            FileWriter newWriter = new FileWriter(data);
            newWriter.write("");
            newWriter.close();

        }catch(IOException ioe) {}

        for(int index = 0; index < scores.size(); index++)
        {
            scores.set(index, 0);
        }

        try {
            FileWriter writer = new FileWriter(data);
            
            for(int index = 0; index < scores.size(); index++)
            {
                writer.append(names.get(index) + ":" + scores.get(index));
                if(index < scores.size() - 1)
                {
                    writer.append("\n");
                }
            }
            writer.close();
        }catch(IOException ioe) {}

        showLeaderboard();
    }

    public void checkHighScore(int score)
    {
        for(int index = 0; index < scores.size(); index++)
        {
            if(score > scores.get(index))
            {
                String congratsPrompt = "You qualify to be added to the ";
                congratsPrompt += "Leaderboard.\n Enter Your Name:";
                String scorersName = JOptionPane.showInputDialog(congratsPrompt);

                write(scorersName, score);

                scores.clear();
                names.clear();
                readFile();
                break;
            }
        }
    }

    public void write(String name, int highScore)
    {

        if(scores.size() < 10)
        {
            try{
                FileWriter writer = new FileWriter(data, true);
                writer.append("\n" + name + ":" + highScore);
                writer.close();
            }catch(IOException ioe){}
        }
        else
        {
            try {

                File tmpFile = new File("./scores.txt.tmp");
                FileReader read = new FileReader(data);
                BufferedReader reader = new BufferedReader(read);
                FileWriter writer = new FileWriter(tmpFile);

                String lineToBeDel = "";
                for(int index = 0; index < scores.size(); index++)
                {
                    if(highScore > scores.get(index))
                    {
                        lineToBeDel += names.get(index) + ":";
                        lineToBeDel += scores.get(index);
                        break;
                    }
                }

                while(reader.readLine() != null)
                {
                    String readLine = reader.readLine();
                    if(readLine != lineToBeDel)
                    {
                        writer.append(readLine + "\n");
                    }
                    else
                    {
                        writer.append(name + ":" + highScore + "\n");
                    }
                }

                reader.close();
                writer.close();

                data.delete();
                tmpFile.renameTo(data);
            }catch(IOException ioe) {}
        }
    }
}
