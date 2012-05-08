package se.starbox.util;

import java.util.List;

import se.starbox.models.User;
import se.starbox.models.UserModel;

/**
 * Downloads indices from friends as a runnable, running in the background.
 * Operates as a singleton.
 */
public class IndexDownloader implements Runnable {
	private static IndexDownloader singleton = null;
	private static long downloadInterval = 10000;
	
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
	 * Sets the download interval of index files (in ms). Default is 10 s.
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
		nextUpdateTicks = System.currentTimeMillis() + downloadInterval;
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
				synchronized (IndexDownloader.this) {
					nextUpdateTicks = ticks + IndexDownloader.downloadInterval;
				}
				
				//List<User> users = UserModel.getWhitelistStatic();
				
				System.out.println("IndexDownloader.run: TODO: Download indices...");
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
}
