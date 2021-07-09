import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

//Music credit to Bensound (https://www.bensound.com)
//This was not taught in APCS, I looked at outside resources to finish this part of the project 

public class Sound {
	public static void main(String[] args) {
		
	}
	public static void music(String musicLocation, long start_time) {
		try {
			File musicPath = new File(musicLocation); 
			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath); 
				Clip clip = AudioSystem.getClip(); 
				clip.open(audioInput); 
				clip.setMicrosecondPosition(start_time);
				clip.start();
				try {
					Thread.sleep(500);
					clip.stop();
				}
				catch (InterruptedException e) {
					e.printStackTrace(); 
				}
				//JOptionPane.showMessageDialog(null, "Press OK to stop playing");
			}
			else {
				System.out.println("Can't find file"); 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}