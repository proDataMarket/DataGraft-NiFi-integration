package com.sintef.nar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.swagger.annotations.ApiOperation;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/upload")
public class JerseyService 
{
	@POST
	@Path("/jar")
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	@Produces({MediaType.MULTIPART_FORM_DATA})
	public Response uploadPdfFile(  @FormDataParam("file") InputStream fileInputStream,
	                                @FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception
	{


		//String UPLOAD_PATH = bundled_dependencies;

		File file = new File("");
		String path = file.getAbsolutePath();

		File fileToDelete = new File(path+"/Auto_Generated_Nar.jar");
		if(fileToDelete.exists()){
			fileToDelete.delete();
		}

		try
	    {
	        int read = 0;
	        byte[] bytes = new byte[1024];
	 
	        OutputStream out = new FileOutputStream(new File(path + "/Auto_Generated_Nar.jar"));
	        while ((read = fileInputStream.read(bytes)) != -1)
	        {
	            out.write(bytes, 0, read);
	        }
	        out.flush();
	        out.close();
	    } catch (IOException e)
	    {
	        throw new WebApplicationException("Error while uploading file. Please try again !!");
	    }

		String JAR_PATH = findJarPath();
		String NAR_PATH = path+"/nifi-AutoGenProcessor-nar-1.0-SNAPSHOT.nar";
		String FILE_NAME = "Auto_Generated_Nar.jar";

		ReadNarContent readNarContent = new ReadNarContent();
		readNarContent.mainJar(JAR_PATH, NAR_PATH, FILE_NAME);


		File fileToDelete2 = new File(path+"/Auto_Generated_Nar.jar");
		if(fileToDelete2.exists()){
			fileToDelete2.delete();
		}

		File f = new File(path+"/nifi-AutoGenProcessor-nar-1.0-SNAPSHOT.nar");

		Response.ResponseBuilder responseBuilder = Response.ok((Object)f);
		responseBuilder.header("Content-Disposition", "attachment; filename=nifi-AutoGenProcessor-nar-1.0-SNAPSHOT.nar");
		// output file must have same name as the name of processor given in MANIFETS.MF file in the MATE-INF folder.

		return responseBuilder.build();

	}


	public String findJarPath(){
		String narMainFolder = "";
		File filePath = new File(""); // finds dynamically the path to java folder.
		String s = filePath.getAbsolutePath(); // turns the path to a string.


		File[] files = new File(s).listFiles(); // traverses all the directories in the main folder pointed to by filepath(s).
		//If this pathname does not denote a directory, then listFiles() returns null.

		for (File file : files) {
			if(file.isFile() &&
					file.getName().equals("Auto_Generated_Nar.jar"))
			{
				narMainFolder = file.getAbsolutePath();
			}
		}
		if(narMainFolder == null)System.out.println("no .nar file found.");

		return narMainFolder;
	}
}
