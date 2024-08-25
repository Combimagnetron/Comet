package me.combimagnetron.comet;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Generator {

    public static void generate() throws URISyntaxException, IOException {
        URL resource = Generator.class.getClassLoader().getResource("patterns.png");
        Path projectPath = new File(resource.toURI()).getParentFile().getParentFile().toPath();
        BufferedImage bufferedImage = ImageIO.read(resource);
        CompilationUnit compilationUnit = new CompilationUnit();
        compilationUnit.setPackageDeclaration("me.combimagnetron.comet.image");
        compilationUnit.addImport(Map.class);
        compilationUnit.addImport(HashMap.class);
        ClassOrInterfaceDeclaration classOrInterfaceDeclaration = compilationUnit.addClass("CanvasSection");
        classOrInterfaceDeclaration.setInterface(true);
        FieldDeclaration map = classOrInterfaceDeclaration.addField("Map<String, CanvasSection>", "VALUES");
        map.getVariable(0).setInitializer("new HashMap<>()");
        HashMap<String, String> fields = new HashMap<>();
        int ch = 57344;
        StringBuilder chars = new StringBuilder();
        for (int x = 0; x < bufferedImage.getHeight(); x = x + 3) {
            for (int y = 0; y < bufferedImage.getWidth(); y = y + 3) {
                String[] pattern = new String[3];
                BufferedImage section = bufferedImage.getSubimage(y, x, 3, 3);
                for (int i = 0; i < 3; i++) {
                    StringBuilder line = new StringBuilder();
                    for (int j = 0; j < 3; j++) {
                        Color color = new Color(section.getRGB(j, i), true);
                        if (color.getRGB() == Color.WHITE.getRGB()) {
                            line.append("1");
                        } else {
                            line.append("0");
                        }
                        pattern[i] = line.toString();
                    }
                }
                int[] size = calculateTrimmedSize(section);
                DecimalFormat formatter = new DecimalFormat("00");
                String name = "R" + formatter.format(x/3) + "_" + formatter.format(y/3);
                FieldDeclaration fieldDeclaration = classOrInterfaceDeclaration.addField("CanvasSection", name);
                int filled = String.join("", pattern).replaceAll("0", "").length();
                fields.put(String.join("", pattern), name);
                fieldDeclaration.getVariable(0).setInitializer("of(new String[] {\"" + pattern[0] + "\", \"" + pattern[1] + "\", \"" + pattern[2] + "\"}," + filled + ", " + size[0] + ", Pos2D.of(" + y/3 + ", " + x/3 + "), '" + (char)ch + "')");
                chars.append((char)ch);
                ch++;
            }
        }
        BlockStmt blockStmt = classOrInterfaceDeclaration.addStaticInitializer();
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            blockStmt.addStatement("VALUES.put(\"" + entry.getKey() + "\", " + entry.getValue() + ");");
        }
        String[] splitString = splitString(chars.toString());

        try (FileWriter writer = new FileWriter(projectPath.resolve(classOrInterfaceDeclaration.getName() + ".java").toFile())) {
            System.out.println(projectPath.toString());
            writer.write(compilationUnit.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter writer = new FileWriter(projectPath.resolve("chars.txt").toFile())) {
            writer.write(String.join("\",\"", splitString));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static int[] calculateTrimmedSize(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        int left = width, top = height, right = -1, bottom = -1;

        // Find bounds of non-empty area
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y), true);
                if (color.getRGB() == Color.WHITE.getRGB()) {
                    left = Math.min(left, x);
                    top = Math.min(top, y);
                    right = Math.max(right, x);
                    bottom = Math.max(bottom, y);
                }
            }
        }

        // If image is empty, return [0, 0]
        if (right < left || bottom < top) {
            return new int[]{0, 0};
        }

        // Calculate and return trimmed width and height
        int trimmedWidth = right - left + 1;
        int trimmedHeight = bottom - top + 1;
        return new int[]{trimmedWidth, trimmedHeight};
    }

    public static String[] splitString(String input) {
        int length = input.length();
        int arraySize = (length + 15) / 16; // Round up division
        String[] result = new String[arraySize];

        for (int i = 0; i < arraySize; i++) {
            int start = i * 16;
            int end = Math.min(start + 16, length);
            result[i] = input.substring(start, end);
        }

        return result;
    }

}
