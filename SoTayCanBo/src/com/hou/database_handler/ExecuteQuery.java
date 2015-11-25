package com.hou.database_handler;

import java.io.IOException;
import java.util.ArrayList;

import com.hou.models.CanBo;
import com.hou.models.DinhKem;
import com.hou.models.DonVi;
import com.hou.models.Event;
import com.hou.models.GhiChu;
import com.hou.models.Group;
import com.hou.models.SoTay;
import com.hou.models.SuKien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class ExecuteQuery {
	protected static final String TAG = "Execute Query";
	SQLiteDatabase database;
	DatabaseHelper mDbHelper;
	private final Context mContext;

	public ExecuteQuery(Context context) {
		this.mContext = context;
		mDbHelper = new DatabaseHelper(mContext);
	}

	public ExecuteQuery createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public ExecuteQuery open() throws SQLException {
		try {
			mDbHelper.openDataBase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	/*
	 * tbl_sukien_tuan
	 */

	// select * from tbl_sukien_tuan
	public ArrayList<SuKien> getAllSukienTuan() {
		ArrayList<SuKien> listSK = new ArrayList<SuKien>();
		String selectQuery = "SELECT * FROM " + ColumnName.SUKIEN_TUAN_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				SuKien sk = new SuKien();

				sk.setMaSukien(cursor.getString(0));
				sk.setThu(cursor.getString(1));
				sk.setBuoi(cursor.getString(2));
				sk.setNgay(cursor.getString(3));
				sk.setTenSukien(cursor.getString(4));
				sk.setPhong(cursor.getString(5));
				sk.setDiadiem(cursor.getString(6));
				sk.setTp_thamgia(cursor.getString(7));
				sk.setThoigianbatdau(cursor.getString(8));
				sk.setTrangthai(cursor.getInt(9));
				sk.setGhichu(cursor.getString(10));
				sk.setNoidung(cursor.getString(11));

				listSK.add(sk);
			} while (cursor.moveToNext());
		}
		return listSK;
	}

	// insert 1 record
	public boolean insert_tblSukienTuan_single(SuKien sk) {
		try {
			database = mDbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			cv.put(ColumnName.SUKIEN_TUAN_MASUKIENTUAN, sk.getMaSukien());
			cv.put(ColumnName.SUKIEN_TUAN_THU, sk.getThu());
			cv.put(ColumnName.SUKIEN_TUAN_BUOI, sk.getBuoi());
			cv.put(ColumnName.SUKIEN_TUAN_NGAY, sk.getNgay());
			cv.put(ColumnName.SUKIEN_TUAN_TENSUKIEN, sk.getTenSukien());
			cv.put(ColumnName.SUKIEN_TUAN_PHONG, sk.getPhong());
			cv.put(ColumnName.SUKIEN_TUAN_DIADIEM, sk.getDiadiem());
			cv.put(ColumnName.SUKIEN_TUAN_TPTHAMGIA, sk.getTp_thamgia());
			cv.put(ColumnName.SUKIEN_TUAN_THOIGIANBATDAU,
					sk.getThoigianbatdau());
			cv.put(ColumnName.SUKIEN_TUAN_TRANGTHAI, sk.getTrangthai());
			cv.put(ColumnName.SUKIEN_TUAN_GHICHU, sk.getGhichu());
			cv.put(ColumnName.SUKIEN_TUAN_NOIDUNG, sk.getNoidung());

			database.insert(ColumnName.SUKIEN_TUAN_TABLE, null, cv);
			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblSukienTuan_single", e.getMessage());
			return false;
		}
	}

	// insert multi record
	public boolean insert_tblSukienTuan_multi(ArrayList<SuKien> listSK) {
		try {
			database = mDbHelper.getWritableDatabase();

			for (SuKien sk : listSK) {
				ContentValues cv = new ContentValues();

				cv.put(ColumnName.SUKIEN_TUAN_MASUKIENTUAN, sk.getMaSukien());
				cv.put(ColumnName.SUKIEN_TUAN_THU, sk.getThu());
				cv.put(ColumnName.SUKIEN_TUAN_BUOI, sk.getBuoi());
				cv.put(ColumnName.SUKIEN_TUAN_NGAY, sk.getNgay());
				cv.put(ColumnName.SUKIEN_TUAN_TENSUKIEN, sk.getTenSukien());
				cv.put(ColumnName.SUKIEN_TUAN_PHONG, sk.getPhong());
				cv.put(ColumnName.SUKIEN_TUAN_DIADIEM, sk.getDiadiem());
				cv.put(ColumnName.SUKIEN_TUAN_TPTHAMGIA, sk.getTp_thamgia());
				cv.put(ColumnName.SUKIEN_TUAN_THOIGIANBATDAU,
						sk.getThoigianbatdau());
				cv.put(ColumnName.SUKIEN_TUAN_TRANGTHAI, sk.getTrangthai());
				cv.put(ColumnName.SUKIEN_TUAN_GHICHU, sk.getGhichu());
				cv.put(ColumnName.SUKIEN_TUAN_NOIDUNG, sk.getNoidung());

				database.insert(ColumnName.SUKIEN_TUAN_TABLE, null, cv);
			}

			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblSukienTuan_multi", e.getMessage());
			return false;
		}
	}

	// delete all record
	public boolean delete_tblSukienTuan_allrecord() {
		try {
			database = mDbHelper.getWritableDatabase();
			database.delete(ColumnName.SUKIEN_TUAN_TABLE, null, null);
			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tblSukienTuan_allrecord", e.getMessage());
			return false;
		}
	}

	/*
	 * END - tbl_sukien_tuan
	 */

	/*
	 * tbl_sotay
	 */

	// Select * from tbl_sotay
	public ArrayList<SoTay> getAllSotay() {
		ArrayList<SoTay> listSotay = new ArrayList<SoTay>();
		String selectQuery = "SELECT * FROM " + ColumnName.SOTAY_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				SoTay st = new SoTay();

				st.setMaSoTay(cursor.getString(0));
				st.setMaCanBo(cursor.getString(1));
				st.setTenSoTay(cursor.getString(2));
				st.setNgayTao(Long.parseLong(cursor.getString(3)));
				st.setSoGhiChu(cursor.getInt(4));

				listSotay.add(st);
			} while (cursor.moveToNext());
		}
		return listSotay;
	}

	// select * from tbl_sotay where maSotay
	public SoTay getSotayByMaSotay(String maSotay) {
		SoTay st = new SoTay();
		String selectQuery = "SELECT * FROM " + ColumnName.SOTAY_TABLE
				+ " WHERE " + ColumnName.SOTAY_MASOTAY + " = '" + maSotay
				+ "' ";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			st.setMaSoTay(cursor.getString(0));
			st.setMaCanBo(cursor.getString(1));
			st.setTenSoTay(cursor.getString(2));
			st.setNgayTao(Long.parseLong(cursor.getString(3)));
			st.setSoGhiChu(cursor.getInt(4));
		}
		return st;
	}

	// insert 1 record
	public boolean insert_tblSotay_single(SoTay st) {
		try {
			database = mDbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			cv.put(ColumnName.SOTAY_MASOTAY, st.getMaSoTay());
			cv.put(ColumnName.SOTAY_MACANBO, st.getMaCanBo());
			cv.put(ColumnName.SOTAY_TENSOTAY, st.getTenSoTay());
			cv.put(ColumnName.SOTAY_NGAYTAO, st.getNgayTao());
			cv.put(ColumnName.SOTAY_SOGHICHU, st.getSoGhiChu());

			database.insert(ColumnName.SOTAY_TABLE, null, cv);
			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblSotay_single", e.getMessage());
			return false;
		}
	}

	// insert multi record
	public boolean insert_tblSotay_mutil(ArrayList<SoTay> listSotay) {
		try {
			database = mDbHelper.getWritableDatabase();

			for (SoTay st : listSotay) {
				ContentValues cv = new ContentValues();

				cv.put(ColumnName.SOTAY_MASOTAY, st.getMaSoTay());
				cv.put(ColumnName.SOTAY_MACANBO, st.getMaCanBo());
				cv.put(ColumnName.SOTAY_TENSOTAY, st.getTenSoTay());
				cv.put(ColumnName.SOTAY_NGAYTAO, st.getNgayTao());
				cv.put(ColumnName.SOTAY_SOGHICHU, st.getSoGhiChu());

				database.insert(ColumnName.SOTAY_TABLE, null, cv);
			}

			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblSotay_single", e.getMessage());
			return false;
		}
	}

	// delete 1 record
	public boolean delete_tblSotay_single(SoTay st) {
		try {
			database = mDbHelper.getWritableDatabase();

			String where = ColumnName.SOTAY_MASOTAY + " = ? ";

			database.delete(ColumnName.SOTAY_TABLE, where,
					new String[] { st.getMaSoTay() });
			database.close();

			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tblSotay_single", e.getMessage());
			return false;
		}
	}

	// delete all record
	public boolean delete_tblSotay_allrecord(SoTay st) {
		try {
			database = mDbHelper.getWritableDatabase();

			String where = ColumnName.SOTAY_MACANBO + " = ? ";

			database.delete(ColumnName.SOTAY_TABLE, where,
					new String[] { st.getMaCanBo() });
			database.close();

			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tblSotay_allrecord", e.getMessage());
			return false;
		}
	}

	// update tenSotay in tbl_sotay
	public boolean update_tblSotay_tenSotay(String maSotay, String tenSotay) {
		try {
			database = mDbHelper.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put(ColumnName.SOTAY_TENSOTAY, tenSotay);

			String where = ColumnName.SOTAY_MASOTAY + " = ? ";

			if (database.update(ColumnName.SOTAY_TABLE, cv, where,
					new String[] { maSotay }) > 0) {
				return true;
			}
			return false;
		} catch (SQLiteException e) {
			Log.e("update_tblSotay_tenSotay", e.getMessage());
			return false;
		}
	}

	// update soGhichu in tbl_sotay
	public boolean update_tblSotay_soGhichu(String maSotay, boolean status) {
		int soGhichu = 0;
		database = mDbHelper.getReadableDatabase();
		String selQ = "SELECT " + ColumnName.SOTAY_SOGHICHU + " FROM "
				+ ColumnName.SOTAY_TABLE + " WHERE " + ColumnName.SOTAY_MASOTAY
				+ " = '" + maSotay + "' ";
		Cursor c = database.rawQuery(selQ, null);
		if (c.moveToFirst()) {
			soGhichu = c.getInt(0);
		}
		if (status) {
			// add new note
			try {
				database = mDbHelper.getWritableDatabase();

				ContentValues cv = new ContentValues();
				cv.put(ColumnName.SOTAY_SOGHICHU, (soGhichu + 1));

				String where = ColumnName.SOTAY_MASOTAY + " = ? ";

				if (database.update(ColumnName.SOTAY_TABLE, cv, where,
						new String[] { maSotay }) > 0) {
					return true;
				}
				return false;
			} catch (SQLiteException e) {
				Log.e("update_tblSotay_soGhichu", e.getMessage());
				return false;
			}
		} else {
			// delete note
			try {
				database = mDbHelper.getWritableDatabase();

				ContentValues cv = new ContentValues();
				cv.put(ColumnName.SOTAY_SOGHICHU, (soGhichu - 1));

				String where = ColumnName.SOTAY_MASOTAY + " = ? ";

				if (database.update(ColumnName.SOTAY_TABLE, cv, where,
						new String[] { maSotay }) > 0) {
					return true;
				}
				return false;
			} catch (SQLiteException e) {
				Log.e("update_tblSotay_soGhichu", e.getMessage());
				return false;
			}
		}
	}

	/*
	 * END - tbl_sotay
	 */

	/*
	 * tbl_group
	 */

	// select * from tbl_group
	public ArrayList<Group> getAllGroup() {
		ArrayList<Group> listGroup = new ArrayList<Group>();
		String selectQuery = "SELECT * FROM " + ColumnName.GROUP_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Group g = new Group();

				g.setMaGroup(cursor.getString(0));
				g.setTenGroup(cursor.getString(1));
				g.setMaCanbo_admin(cursor.getString(2));

				listGroup.add(g);
			} while (cursor.moveToNext());
		}
		return listGroup;
	}

	// insert 1 record
	public boolean insert_tblGroup_single(Group g) {
		try {
			database = mDbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			cv.put(ColumnName.GROUP_MAGROUP, g.getMaGroup());
			cv.put(ColumnName.GROUP_TENGROUP, g.getTenGroup());
			cv.put(ColumnName.GROUP_MACANBO_ADMIN, g.getMaCanbo_admin());

			database.insert(ColumnName.GROUP_TABLE, null, cv);
			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblGroup_single", e.getMessage());
			return false;
		}
	}

	// insert multi record
	public boolean insert_tblGroup_multi(ArrayList<Group> listGroup) {
		try {
			database = mDbHelper.getWritableDatabase();

			for (Group g : listGroup) {
				ContentValues cv = new ContentValues();

				cv.put(ColumnName.GROUP_MAGROUP, g.getMaGroup());
				cv.put(ColumnName.GROUP_TENGROUP, g.getTenGroup());
				cv.put(ColumnName.GROUP_MACANBO_ADMIN, g.getMaCanbo_admin());

				database.insert(ColumnName.GROUP_TABLE, null, cv);
			}
			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblGroup_single", e.getMessage());
			return false;
		}
	}

	// delete 1 record
	public boolean delete_tblGroup_single(Group g) {
		try {
			database = mDbHelper.getWritableDatabase();

			String where = ColumnName.GROUP_MAGROUP + " = ? ";

			database.delete(ColumnName.GROUP_TABLE, where,
					new String[] { g.getMaGroup() });
			database.close();

			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tblGroup_single", e.getMessage());
			return false;
		}
	}

	// delete all record
	public boolean delete_tblGroup_allrecord() {
		try {
			database = mDbHelper.getWritableDatabase();
			database.delete(ColumnName.GROUP_TABLE, null, null);
			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tblGroup_allrecord", e.getMessage());
			return false;
		}
	}

	// update tenGroup in tblGroup
	public boolean update_tblGroup_tenGroup(String tenGroup, String maGroup) {
		try {
			database = mDbHelper.getWritableDatabase();

			ContentValues cv = new ContentValues();
			cv.put(ColumnName.GROUP_TENGROUP, tenGroup);

			String where = ColumnName.GROUP_MAGROUP + " = ? ";

			if (database.update(ColumnName.GROUP_TABLE, cv, where,
					new String[] { maGroup }) > 0) {
				return true;
			}
			return false;
		} catch (SQLiteException e) {
			Log.e("update_tblGroup_tenGroup", e.getMessage());
			return false;
		}
	}

	/*
	 * END - tbl_group
	 */

	/*
	 * tbl_ghichu
	 */

	// select * from tbl_ghichu
	public ArrayList<GhiChu> getAllGhichu() {
		ArrayList<GhiChu> listGhichu = new ArrayList<GhiChu>();
		String selectQuery = "SELECT * FROM " + ColumnName.GHICHU_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				GhiChu gc = new GhiChu();

				gc.setMaGhiChu(cursor.getString(0));
				gc.setTenGhiChu(cursor.getString(1));
				gc.setNgaytao(cursor.getLong(2));
				gc.setNoidung(cursor.getString(3));
				gc.setNgaysua(cursor.getLong(4));
				gc.setTrangthai(cursor.getInt(5));
				gc.setDinhkem(cursor.getInt(6));
				gc.setNgaythuchien(cursor.getLong(7));
				gc.setBookmark(cursor.getInt(8));
				gc.setMaSotay(cursor.getString(9));

				listGhichu.add(gc);
			} while (cursor.moveToNext());
		}
		return listGhichu;
	}

	// select * from tbl_ghichu where maSotay
	public ArrayList<GhiChu> getAllGhichuByMasotay(String maSotay) {
		ArrayList<GhiChu> listGhichu = new ArrayList<GhiChu>();
		String selectQuery = "SELECT * FROM " + ColumnName.GHICHU_TABLE
				+ " WHERE " + ColumnName.GHICHU_MASOTAY + " = '" + maSotay
				+ "' ";
		;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				GhiChu gc = new GhiChu();

				gc.setMaGhiChu(cursor.getString(0));
				gc.setTenGhiChu(cursor.getString(1));
				gc.setNgaytao(cursor.getLong(2));
				gc.setNoidung(cursor.getString(3));
				gc.setNgaysua(cursor.getLong(4));
				gc.setTrangthai(cursor.getInt(5));
				gc.setDinhkem(cursor.getInt(6));
				gc.setNgaythuchien(cursor.getLong(7));
				gc.setBookmark(cursor.getInt(8));
				gc.setMaSotay(cursor.getString(9));

				listGhichu.add(gc);
			} while (cursor.moveToNext());
		}
		return listGhichu;
	}

	// select * from tbl_ghichu where maGhichu
	public GhiChu getGhichuByMaghichu(String maGhichu) {
		GhiChu gc = new GhiChu();
		String selectQuery = "SELECT * FROM " + ColumnName.GHICHU_TABLE
				+ " WHERE " + ColumnName.GHICHU_MAGHICHU + " = '" + maGhichu
				+ "' ";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			gc.setMaGhiChu(cursor.getString(0));
			gc.setTenGhiChu(cursor.getString(1));
			gc.setNgaytao(cursor.getLong(2));
			gc.setNoidung(cursor.getString(3));
			gc.setNgaysua(cursor.getLong(4));
			gc.setTrangthai(cursor.getInt(5));
			gc.setDinhkem(cursor.getInt(6));
			gc.setNgaythuchien(cursor.getLong(7));
			gc.setBookmark(cursor.getInt(8));
			gc.setMaSotay(cursor.getString(9));
		}
		return gc;
	}

	// insert 1 record
	public boolean insert_tblGhichu_single(GhiChu g) {
		try {
			database = mDbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			cv.put(ColumnName.GHICHU_MAGHICHU, g.getMaGhiChu());
			cv.put(ColumnName.GHICHU_TENGHICHU, g.getTenGhiChu());
			cv.put(ColumnName.GHICHU_NGAYTAO, g.getNgaytao());
			cv.put(ColumnName.GHICHU_NOIDUNG, g.getNoidung());
			cv.put(ColumnName.GHICHU_NGAYSUA, g.getNgaysua());
			cv.put(ColumnName.GHICHU_TRANGTHAI, g.getTrangthai());
			cv.put(ColumnName.GHICHU_DINHKEM, g.getDinhkem());
			cv.put(ColumnName.GHICHU_NGAYTHUCHIEN, g.getNgaythuchien());
			cv.put(ColumnName.GHICHU_BOOKMARK, g.getBookmark());
			cv.put(ColumnName.GHICHU_MASOTAY, g.getMaSotay());

			database.insert(ColumnName.GHICHU_TABLE, null, cv);

			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblGhichu_single", e.getMessage());
			return false;
		}
	}

	// update Ghichu
	public boolean update_tblGhichu(GhiChu gc) {
		try {
			database = mDbHelper.getWritableDatabase();

			ContentValues cv = new ContentValues();

			cv.put(ColumnName.GHICHU_TENGHICHU, gc.getTenGhiChu());
			cv.put(ColumnName.GHICHU_NOIDUNG, gc.getNoidung());
			cv.put(ColumnName.GHICHU_NGAYSUA, gc.getNgaysua());
			cv.put(ColumnName.GHICHU_TRANGTHAI, gc.getTrangthai());
			cv.put(ColumnName.GHICHU_DINHKEM, gc.getDinhkem());
			cv.put(ColumnName.GHICHU_NGAYTHUCHIEN, gc.getNgaythuchien());
			cv.put(ColumnName.GHICHU_BOOKMARK, gc.getBookmark());
			cv.put(ColumnName.GHICHU_MASOTAY, gc.getMaSotay());

			String where = ColumnName.GHICHU_MAGHICHU + " = ? ";

			if (database.update(ColumnName.GHICHU_TABLE, cv, where,
					new String[] { gc.getMaGhiChu() }) > 0) {
				return true;
			}
			return false;
		} catch (SQLiteException e) {
			Log.e("update_tblGhichu", e.getMessage());
			return false;
		}
	}

	/*
	 * END - tbl_ghichu
	 */

	/*
	 * tbl_dinhkem
	 */
	// select DinhKem by maGhichu
	public ArrayList<DinhKem> getAllDinhKemByMaGhiChu(String maGhichu) {
		ArrayList<DinhKem> listDinhkem = new ArrayList<DinhKem>();
		String selectQuery = "SELECT * FROM " + ColumnName.DINHKEM_TABLE
				+ " WHERE " + ColumnName.DINHKEM_MAGHICHU + " = '" + maGhichu
				+ "' ";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				DinhKem dk = new DinhKem();

				dk.setMaDinhkem(cursor.getString(0));
				dk.setMaGhichu(cursor.getString(1));
				dk.setLoaifile(cursor.getString(2));
				dk.setUrl(cursor.getString(3));
				dk.setFilename(cursor.getString(4));

				listDinhkem.add(dk);
			} while (cursor.moveToNext());
		}
		return listDinhkem;
	}

	// insert 1 record
	public boolean insert_tblDinhkem_single(DinhKem d) {
		try {
			database = mDbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			cv.put(ColumnName.DINHKEM_MADINHKEM, d.getMaDinhkem());
			cv.put(ColumnName.DINHKEM_MAGHICHU, d.getMaGhichu());
			cv.put(ColumnName.DINHKEM_LOAIFILE, d.getLoaifile());
			cv.put(ColumnName.DINHKEM_URL, d.getUrl());
			cv.put(ColumnName.DINHKEM_FILENAME, d.getFilename());

			database.insert(ColumnName.DINHKEM_TABLE, null, cv);

			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblDinhkem_single", e.getMessage());
			return false;
		}
	}

	/*
	 * END - tbl_dinhkem
	 */

	/*
	 * tbl_donvi
	 */
	// select * from tbl_donvi
	public ArrayList<DonVi> getAllDonvi() {
		ArrayList<DonVi> listDV = new ArrayList<DonVi>();
		String selectQuery = "SELECT * FROM " + ColumnName.DONVI_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				DonVi dv = new DonVi();

				dv.setMaDonvi(cursor.getString(0));
				dv.setTenDonvi(cursor.getString(1));
				dv.setSodienthoai(cursor.getString(2));
				dv.setEmail(cursor.getString(3));
				dv.setWebsite(cursor.getString(4));
				dv.setFax(cursor.getString(5));
				dv.setDiachi(cursor.getString(6));
				dv.setMaNhomdonvi(cursor.getString(7));

				listDV.add(dv);
			} while (cursor.moveToNext());
		}
		return listDV;
	}

	// select tenDonvi by maDonvi
	public String getTendonviByMadonvi(String maDonvi) {
		String tenDonvi = null;
		String selectQuery = "SELECT " + ColumnName.DONVI_TENDONVI + " FROM "
				+ ColumnName.DONVI_TABLE + " WHERE " + ColumnName.DONVI_MADONVI
				+ " = '" + maDonvi + "' ";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			tenDonvi = cursor.getString(0);
		}
		return tenDonvi;
	}

	// insert multi record
	public boolean insert_tblDonvi_multi(ArrayList<DonVi> listDV) {
		try {
			database = mDbHelper.getWritableDatabase();

			for (DonVi dv : listDV) {
				ContentValues cv = new ContentValues();

				cv.put(ColumnName.DONVI_MADONVI, dv.getMaDonvi());
				cv.put(ColumnName.DONVI_TENDONVI, dv.getTenDonvi());
				cv.put(ColumnName.DONVI_SODIENTHOAI, dv.getSodienthoai());
				cv.put(ColumnName.DONVI_EMAIL, dv.getEmail());
				cv.put(ColumnName.DONVI_WEBSITE, dv.getWebsite());
				cv.put(ColumnName.DONVI_FAX, dv.getFax());
				cv.put(ColumnName.DONVI_DIACHI, dv.getDiachi());
				cv.put(ColumnName.DONVI_MANHOMDONVI, dv.getMaNhomdonvi());

				database.insert(ColumnName.DONVI_TABLE, null, cv);
			}

			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblDonvi_multi", e.getMessage());
			return false;
		}
	}

	// insert single record
	public boolean insert_tblDonvi_single(DonVi dv) {
		try {
			database = mDbHelper.getWritableDatabase();

			ContentValues cv = new ContentValues();

			cv.put(ColumnName.DONVI_MADONVI, dv.getMaDonvi());
			cv.put(ColumnName.DONVI_TENDONVI, dv.getTenDonvi());
			cv.put(ColumnName.DONVI_SODIENTHOAI, dv.getSodienthoai());
			cv.put(ColumnName.DONVI_EMAIL, dv.getEmail());
			cv.put(ColumnName.DONVI_WEBSITE, dv.getWebsite());
			cv.put(ColumnName.DONVI_FAX, dv.getFax());
			cv.put(ColumnName.DONVI_DIACHI, dv.getDiachi());
			cv.put(ColumnName.DONVI_MANHOMDONVI, dv.getMaNhomdonvi());

			database.insert(ColumnName.DONVI_TABLE, null, cv);

			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblDonvi_single", e.getMessage());
			return false;
		}
	}

	// delete all record
	public boolean delete_tblDonvi_allrecord() {
		try {
			database = mDbHelper.getWritableDatabase();
			database.delete(ColumnName.DONVI_TABLE, null, null);
			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tblDonvi_allrecord", e.getMessage());
			return false;
		}
	}

	/*
	 * END - tbl_donvi
	 */

	/*
	 * tbl_event
	 */
	// select * from tbl_event
	public ArrayList<Event> getAllEvent() {
		ArrayList<Event> listEvent = new ArrayList<Event>();
		String selQuery = " SELECT * FROM " + ColumnName.EVENT_TABLE;
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Event e = new Event();

				e.setMaEvent(cursor.getString(0));
				e.setTenEvent(cursor.getString(1));
				e.setDiadiem(cursor.getString(2));
				e.setThoigianbatdau(cursor.getString(3));
				e.setMaLaplai(cursor.getString(4));
				e.setMaLoinhac(cursor.getString(5));
				e.setNgay_event(cursor.getString(6));
				e.setMaCanbo(cursor.getString(7));
				e.setMota(cursor.getString(8));

				listEvent.add(e);
			} while (cursor.moveToNext());
		}
		return listEvent;
	}

	// select * from tbl_event where ngay_event
	public ArrayList<Event> getAllEventByNgayevent(String ngay_event) {
		ArrayList<Event> listEvent = new ArrayList<Event>();
		String selQuery = " SELECT * FROM " + ColumnName.EVENT_TABLE
				+ " WHERE " + ColumnName.EVENT_NGAYEVENT + " = '" + ngay_event
				+ "' ";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selQuery, null);
		if (cursor.moveToFirst()) {
			do {
				Event e = new Event();

				e.setMaEvent(cursor.getString(0));
				e.setTenEvent(cursor.getString(1));
				e.setDiadiem(cursor.getString(2));
				e.setThoigianbatdau(cursor.getString(3));
				e.setMaLaplai(cursor.getString(4));
				e.setMaLoinhac(cursor.getString(5));
				e.setNgay_event(cursor.getString(6));
				e.setMaCanbo(cursor.getString(7));
				e.setMota(cursor.getString(8));

				listEvent.add(e);
			} while (cursor.moveToNext());
		}
		return listEvent;
	}

	// delete all record
	public boolean delete_tblEvent_allrecord() {
		try {
			database = mDbHelper.getWritableDatabase();
			database.delete(ColumnName.EVENT_TABLE, null, null);
			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tblEvent_allrecord", e.getMessage());
			return false;
		}
	}

	// delete event by maEvent
	public boolean delete_tblEvent_byMaEvent(String maEvent) {
		try {
			database = mDbHelper.getWritableDatabase();
			String where = ColumnName.EVENT_MAEVENT + " = ? ";
			database.delete(ColumnName.EVENT_TABLE, where,
					new String[] { maEvent });
			database.close();
			return true;
		} catch (SQLiteException e) {
			Log.e("delete_tblEvent_allrecord", e.getMessage());
			database.close();
			return false;
		}
	}

	// insert single record
	public boolean insert_tblEvent_single(Event e) {
		try {
			database = mDbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			cv.put(ColumnName.EVENT_MAEVENT, e.getMaEvent());
			cv.put(ColumnName.EVENT_TENEVENT, e.getTenEvent());
			cv.put(ColumnName.EVENT_DIADIEM, e.getDiadiem());
			cv.put(ColumnName.EVENT_THOIGIANBATDAU, e.getThoigianbatdau());
			cv.put(ColumnName.EVENT_MALAPLAI, e.getMaLaplai());
			cv.put(ColumnName.EVENT_MALOINHAC, e.getMaLoinhac());
			cv.put(ColumnName.EVENT_NGAYEVENT, e.getNgay_event());
			cv.put(ColumnName.EVENT_MACANBO, e.getMaCanbo());
			cv.put(ColumnName.EVENT_MOTA, e.getMota());

			if (database.insert(ColumnName.EVENT_TABLE, null, cv) != -1) {
				return true;
			} else {
				return false;
			}
		} catch (SQLiteException e2) {
			Log.e("insert_tblEvent_single", e2.getMessage());
			return false;
		}
	}

	/*
	 * END - tbl_event
	 */

	/*
	 * tbl_canbo
	 */
	// select * from tbl_canbo where maDonvi
	public ArrayList<CanBo> getAllCanboFromDV(String maDonvi) {
		ArrayList<CanBo> listCB = new ArrayList<CanBo>();
		String selectQuery = "SELECT * FROM " + ColumnName.CANBO_TABLE
				+ " WHERE " + ColumnName.CANBO_MADONVI + " = '" + maDonvi
				+ "' ";
		database = mDbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				CanBo cb = new CanBo();

				cb.setMaCanbo(cursor.getString(0));
				cb.setTenCanbo(cursor.getString(1));
				cb.setDiachi(cursor.getString(2));
				cb.setEmail(cursor.getString(3));
				cb.setCmnd(cursor.getString(4));
				cb.setHocham(cursor.getString(5));
				cb.setHocvi(cursor.getString(6));
				cb.setAvatar(cursor.getString(7));
				cb.setMaDonvi(cursor.getString(8));
				cb.setSdt(cursor.getString(9));

				listCB.add(cb);
			} while (cursor.moveToNext());
		}
		return listCB;
	}

	// insert single record
	public boolean insert_tblCanbo_single(CanBo d) {
		try {
			database = mDbHelper.getWritableDatabase();
			ContentValues cv = new ContentValues();

			cv.put(ColumnName.CANBO_MACANBO, d.getMaCanbo());
			cv.put(ColumnName.CANBO_TENCANBO, d.getTenCanbo());
			cv.put(ColumnName.CANBO_DIACHI, d.getDiachi());
			cv.put(ColumnName.CANBO_EMAIL, d.getEmail());
			cv.put(ColumnName.CANBO_CMND, d.getCmnd());
			cv.put(ColumnName.CANBO_HOCHAM, d.getHocham());
			cv.put(ColumnName.CANBO_HOCVI, d.getHocvi());
			cv.put(ColumnName.CANBO_AVATAR, d.getAvatar());
			cv.put(ColumnName.CANBO_MADONVI, d.getMaDonvi());
			cv.put(ColumnName.CANBO_SDT, d.getSdt());

			database.insert(ColumnName.CANBO_TABLE, null, cv);

			return true;
		} catch (SQLiteException e) {
			Log.e("insert_tblCanbo_single", e.getMessage());
			return false;
		}
	}

	/*
	 * END - tbl_canbo
	 */
}
