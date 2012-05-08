package se.starbox.util;

import java.io.File;
import java.util.List;

import se.starbox.models.FileSystemModel;
import se.starbox.models.SettingsModel;
import se.starbox.models.User;
import se.starbox.models.UserModel;

/**
 * Downloads indices from friends as a runnable, running in the background.
 * Operates as a singleton.
 */
public class IndexDownloader implements Runnable {
	private static IndexDownloader singleton = null;
	private static long downloadInterval = 60000;
	
	private boolean doStop = false;
	private long nextUpdateTicks;
	
	/**
	 * Starts the IndexDownloader in the background, if not already started.
	 */
	public static synchronized void start() {
		System.out.println("IndexDownloader.start called.");
		if (singleton == null) {
			singleton = new IndexDownloader();
			new Thread(singleton).start();
		}
	}
	
	/**
	 * Sets the download interval of index files (in ms). Default is 60 s.
	 */
	public static synchronized void setDownloadInterval(long downloadInterval) {
		IndexDownloader.downloadInterval = downloadInterval;
	}
	
	/**
	 * Stops the IndexDownloader if currently running.
	 */
	public static synchronized void stop() {
		System.out.println("IndexDownloader.stop called.");
		if (singleton == null) {
			synchronized (singleton) {
				singleton.doStop = true;
			}
			singleton = null;
		}
	}
	
	private IndexDownloader() {
		nextUpdateTicks = System.currentTimeMillis()/* + downloadInterval*/;
	}
	
	@Override
	public void run() {
		while (true) {
			// Has been requested to stop?
			synchronized (this) {
				if (doStop)
					return;
			}
			
			long ticks = System.currentTimeMillis();
			if (ticks >= nextUpdateTicks) {
				// Time to download indices
				synchronized (IndexDownloader.class) {
					nextUpdateTicks = ticks + IndexDownloader.downloadInterval;
				}

				System.out.println("IndexDownloader.run: Time to download indices...");
				
				// Get users
				List<User> users;
				synchronized (UserModel.class) {
					users = UserModel.getWhitelistStatic();
				}
				
				String indexFolder = SettingsModel.getProjectRootPath();
				
				for (User user : users) {
					// Download from user
					String sourceUrl = "http://" + user.getIp() + ":8080/starbox/file?index";
					File destinationFile = new File(indexFolder + "Index/IndexFile_" + user.getIp() + ".xml");
					
					new Thread(new DownloadFile(sourceUrl, destinationFile)).start();
					
					//System.out.println("IndexDownloader.run: Source: " + sourceUrl + ", Destination: " + destinationFile.getAbsolutePath());
				}
			}
			else {
				// Sleep until time to download indices
				try {
					Thread.sleep(nextUpdateTicks - ticks);
				} catch (InterruptedException e) {
					System.out.println("IndexDownloader.run: Thread.sleep => InterruptedException");
				}
			}
		}
	}
	
	/**
	 * A runnable for downloading a file from a URL (over HTTP).
	 */
	public static class DownloadFile implements Runnable {
		private String sourceUrl;
		private File destinationFile;
		
		public DownloadFile(String sourceUrl, File destinationFile) {
			this.sourceUrl       = sourceUrl;
			this.destinationFile = destinationFile;
		}
		
		@Override
		public void run() {
			FileSystemModel.DownloadFileResult result = FileSystemModel.downloadFile(sourceUrl, destinationFile);
			if (result == FileSystemModel.DownloadFileResult.SUCCESS) {
				System.out.println("DownloadFile (" + sourceUrl + "): File downloaded successfully and saved at " + destinationFile.getAbsolutePath());
			}
			else if (result == FileSystemModel.DownloadFileResult.EARLY_FAIL) {
				System.out.println("DownloadFile (" + sourceUrl + "): File downloaded 'early' fail, " + destinationFile.getAbsolutePath() + " was untouched.");
			}
			else {
				System.out.println("DownloadFile (" + sourceUrl + "): File downloaded interrupted. Corrupted file to be deleted (" + destinationFile.getAbsolutePath() + ").");
				if (destinationFile.exists()) {
					if (!destinationFile.delete())
						System.out.println("DownloadFile (" + sourceUrl + "): Unable to delete " + destinationFile.getAbsolutePath());
				}
			}
		}
	}
}
