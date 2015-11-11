package com.hou.ultis;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import com.hou.app.Const;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

public class ImageUltiFunctions {
	public static File getFileFromUri(String imgUri) {
		// TODO Auto-generated method stub

		try {
			URI uri = URI.create(imgUri);
			File file = new File(uri);
			if (file != null) {
				if (file.canRead()) {
					return file;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public static String getRealPathFromURI(String contentURI, Context context) {
		Uri contentUri = Uri.parse(contentURI);

		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = null;
		try {
			if (Build.VERSION.SDK_INT > 19) {
				// Will return "image:x*"
				String wholeID = DocumentsContract.getDocumentId(contentUri);
				// Split at colon, use second item in the array
				String id = wholeID.split(":")[1];
				// where id is equal to
				String sel = MediaStore.Images.Media._ID + "=?";

				cursor = context.getContentResolver().query(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						projection, sel, new String[] { id }, null);
			} else {
				cursor = context.getContentResolver().query(contentUri,
						projection, null, null, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String path = null;
		try {
			int column_index = cursor
					.getColumnIndex(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			path = cursor.getString(column_index).toString();
			cursor.close();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return path;
	}

	public static Bitmap decodeSampledBitmapFromFile(File file, int reqWidth,
			int reqHeight) {
		// TODO Auto-generated method stub
		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(file.getAbsolutePath(), options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
	}

	/** Calculate the scaling factor */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (height > reqHeight || width > reqWidth) {
			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}

	@SuppressWarnings("unused")
	public static void downloadFileFromServer(String filename) {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		int count;		
		
		try {
			URL url = new URL(Const.URL_DOWNLOAD_AVATAR + filename);
			URLConnection conection = url.openConnection();
			conection.connect();
			// getting file length
			File mediaStorageDir;
			if (Build.VERSION.SDK_INT > 8) {
				mediaStorageDir = Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			} else {
				mediaStorageDir = new File(
						Environment.getExternalStorageDirectory(), "Pictures");
			}
			if (!mediaStorageDir.exists()) {
				if (mediaStorageDir.mkdirs() || mediaStorageDir.isDirectory()) {

				}
			}

			Log.d("Url", url.toString());

			// input stream to read file - with 8k buffer
			InputStream input = new BufferedInputStream(url.openStream(), 8192);

			// Output stream to write file

			Log.d("Out", mediaStorageDir.getPath() + "/" + filename);
			OutputStream output = new FileOutputStream(
					mediaStorageDir.getPath() + "/" + filename);

			byte data[] = new byte[1024];

			long total = 0;

			while ((count = input.read(data)) != -1) {
				output.write(data, 0, count);
			}

			// flushing output
			output.flush();

			// closing streams
			output.close();
			input.close();

		} catch (Exception e) {
			Log.e("downloadFileFromServer", e.getMessage());
		}
	}
	
}
