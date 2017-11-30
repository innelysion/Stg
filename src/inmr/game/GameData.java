package inmr.game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

public class GameData {

	private final String path = "image/base";
	HashMap<String, BufferedImage> imageStorage = new HashMap<String, BufferedImage>();

	GameData(){
		loadResource();
	}

	void loadResource() {
		ArrayList<String> imgres = new ArrayList<String>();
		try {
			String p = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(),
					"UTF-8");
			if (p.contains(".jar")) {
				JarFile jar = new JarFile(p);
				Enumeration<JarEntry> es = jar.entries();
				while (es.hasMoreElements()) {
					JarEntry e = es.nextElement();
					String name = e.getName();
					if (name.contains(".jpg") || name.contains(".png")) {
						imgres.add(name.substring(name.lastIndexOf("/") + 1));
					}
				}
			} else {
				imgres.addAll(Arrays.asList(new File(getClass().getResource(path).getPath()).list()));
			}
			for (String name : imgres) {
				imageStorage.put(name, ImageIO.read(getClass().getResource(path + "/" + name)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
