package ec.gob.ambiente.api.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import lombok.Getter;
import lombok.Setter;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import ec.gob.ambiente.api.model.DataResponse;

@Path("/file")
public class FileResource {

	// TODO REPLACE WITH CUSTOM EVIDENCE DIR
	//private static final String DIR_FILES = System.getProperty("java.io.tmpdir");
	public static final String DIR_FILES = "/opt/manglarFiles";
	private final int chunk_size = 1024 * 1024 * 2; // 2 MB chunks

	@POST
	@Path("/upload")  //Your Path or URL to call this service
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public DataResponse upload(MultipartFormDataInput input) {
		try {
			InputStream uploadedInputStream = input.getFormDataPart("file", InputStream.class, null);
	        String fileName = input.getFormDataPart("name", String.class, null);
			File folder = new File(DIR_FILES);
			if ( !folder.exists() ) {
				return new DataResponse("ERROR: El directorio principal no existe");
			}
		    File objFile = new File(folder, fileName);
		    if ( objFile.exists() ) {
		       objFile.delete();
		    }
		    saveToFile(uploadedInputStream, objFile);
			return new DataResponse(DataResponse.SUCCESS_STATE);
        } catch (IOException e) {
			e.printStackTrace();
		}
		return new DataResponse("ERROR: El archivo no se pudo guardar");
	}

	public static boolean saveFile(File file, String fileName){
		File folder = new File(DIR_FILES);
		if ( !folder.exists() ) {
			return false;
		}
	    File objFile = new File(folder, fileName);
	    if ( objFile.exists() ) {
	       objFile.delete();
	    }
		try {
            byte[] bytes = Files.readAllBytes(file.toPath());
        	OutputStream out = new FileOutputStream(objFile);
            out.write(bytes);
	        out.flush();
	        out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return false;
	}

	public static void saveToFile(InputStream uploadedInputStream, File fileToSave) {
	    try {
	        int read = 0;
	        OutputStream out = new FileOutputStream(fileToSave);
	        byte[] bytes = new byte[1024 * 1024 * 130];

	        while ((read = uploadedInputStream.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        uploadedInputStream.close();
	        out.flush();
	        out.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	@GET
	@Path("/images/{name-file}")
	@Produces("image/png")
	public Response getImageFile(@PathParam("name-file") String nameFile) {
		String pathFile = DIR_FILES + File.separator + nameFile;
		File file = new File(pathFile);
		if (!file.exists()) {
	        ResponseBuilder rBuild = Response.status(Response.Status.BAD_REQUEST);
	        String msgError = "Recurso no disponible";
	        return rBuild.type(MediaType.TEXT_PLAIN).entity(msgError).build();
		}
		ResponseBuilder response = Response.ok((Object) file);
		//response.header("Content-Disposition", "attachment; filename=\"" + nameFile + "\"");
		return response.build();
	}

	@GET
	@Path("/documents/{name-file}")
	@Produces("application/pdf")
	public Response getDocument(@PathParam("name-file") String nameFile) {
		String pathFile = DIR_FILES + File.separator + nameFile;
		File file = new File(pathFile);
		if (!file.exists()) {
	        ResponseBuilder rBuild = Response.status(Response.Status.BAD_REQUEST);
	        String msgError = "Recurso no disponible";
	        return rBuild.type(MediaType.TEXT_PLAIN).entity(msgError).build();
		}
		ResponseBuilder response = Response.ok((Object) file);
		if (nameFile.indexOf(".pdf") < 0 ) {
			response.header("Content-Disposition", "attachment; filename=\"" + nameFile + "\"");
		}
		return response.build();
	}

	@GET
	@Path("/geojsons/{name-file}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGeojsons(@PathParam("name-file") String nameFile) {
		String pathFile = DIR_FILES + File.separator + nameFile;
		File file = new File(pathFile);
		if (!file.exists()) {
	        ResponseBuilder rBuild = Response.status(Response.Status.BAD_REQUEST);
	        String msgError = "Recurso no disponible";
	        return rBuild.type(MediaType.TEXT_PLAIN).entity(msgError).build();
		}
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=\"" + nameFile + "\"");
		return response.build();
	}

	@GET
	@Path("/videos/{name-file}")
	@Produces("video/mp4")
	public Response video(@PathParam("name-file") String nameFile, @HeaderParam("Range") String range) {
	    String pathFile = DIR_FILES + File.separator + nameFile;
		File file = new File(pathFile);
		if (!file.exists()) {
	        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity("Recurso no disponible").build();
		}
		try{
			return buildStream( file, range );
		}catch(Exception e){
			e.printStackTrace();
		}
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity("Recurso no disponible").build();
	}

	/**
     * @param asset Media file
     * @param range range header
     * @return Streaming output
     * @throws Exception IOException if an error occurs in streaming.
     */
    private Response buildStream( final File asset, final String range ) throws Exception {
        if (range == null) {
            StreamingOutput streamer = new StreamingOutput() {
                @Override
                public void write(final OutputStream output) throws IOException, WebApplicationException {
                    @SuppressWarnings("resource")
					final FileChannel inputChannel = new FileInputStream(asset).getChannel();
                    final WritableByteChannel outputChannel = Channels.newChannel(output);
                    try {
                        inputChannel.transferTo(0, inputChannel.size(), outputChannel);
                    } finally {
                        inputChannel.close();
                        outputChannel.close();
                    }
                }
            };
            return Response.ok(streamer)
            		.status( Response.Status.OK )
            		.header(HttpHeaders.CONTENT_LENGTH, asset.length())
            		.build();
        }
        
        String[] ranges = range.split( "=" )[1].split( "-" );
        
        int from = Integer.parseInt( ranges[0] );
        
        // Chunk media if the range upper bound is unspecified
        int to = chunk_size + from;
        
        if ( to >= asset.length() ) {
            to = (int) ( asset.length() - 1 );
        }
        
        // uncomment to let the client decide the upper bound
        // we want to send 2 MB chunks all the time
        //if ( ranges.length == 2 ) {
        //    to = Integer.parseInt( ranges[1] );
        //}
        
        final String responseRange = String.format( "bytes %d-%d/%d", from, to, asset.length() );
        final RandomAccessFile raf = new RandomAccessFile( asset, "r" );
        raf.seek( from );

        final int len = to - from + 1;
        final MediaStreamer mediaStreamer = new MediaStreamer( len, raf );

        return Response.ok( mediaStreamer )
                .status( 206 )
                .header( "Accept-Ranges", "bytes" )
                .header( "Content-Range", responseRange )
                .header( HttpHeaders.CONTENT_LENGTH, mediaStreamer. getLenth() )
                .header( HttpHeaders.LAST_MODIFIED, new Date( asset.lastModified() ) )
                .build();
    }
    
}


class MediaStreamer implements StreamingOutput {
    private int length;
    private RandomAccessFile raf;
    final byte[] buf = new byte[4096];
    public MediaStreamer( int length, RandomAccessFile raf ) {
        this.length = length;
        this.raf = raf;
    }
    @Override
    public void write( OutputStream outputStream ) throws IOException, WebApplicationException {
        try {
            while( length != 0) {
                int read = raf.read( buf, 0, buf.length > length ? length : buf.length );
                outputStream.write( buf, 0, read );
                length -= read;
            }
        } 
        finally {
            raf.close();
        }
    }
    public int getLenth() {
        return length;
    }
}
