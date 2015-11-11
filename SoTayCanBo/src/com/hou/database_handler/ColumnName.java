package com.hou.database_handler;

public class ColumnName {
	
	/*
	 * dm_chucvu
	 */
	public static String DMCHUCVU_TABLE = "dm_chucvu";
	public static String DMCHUCVU_MADMCHUCVU = "maDmChucvu";
	public static String DMCHUCVU_TENDMCHUCVU = "tenChucvu";
	public static String DMCHUCVU_GHICHU = "ghichu";

	/*
	 * dm_laplai
	 */
	public static String DMLAPLAI_TABLE = "dm_laplai";
	public static String DMLAPLAI_MALAPLAI = "maLaplai";
	public static String DMLAPLAI_TENLAPLAI = "tenLaplai";
	public static String DMLAPLAI_THOIGIAN = "thoigian";

	/*
	 * dm_loaidienthoai
	 */
	public static String DMLOAIDIENTHOAI_TABLE = "dm_loaidienthoai";
	public static String DMLOAIDIENTHOAI_MALOAIDIENTHOAI = "maLoaidienthoai";
	public static String DMLOAIDIENTHOAI_TENLOAIDIENTHOAI = "tenLoaidienthoai";

	/*
	 * dm_loinhac
	 */
	public static String DMLOINHAC_TABLE = "dm_loinhac";
	public static String DMLOINHAC_MALOINHAC = "maLoinhac";
	public static String DMLOINHAC_TENLOINHAC = "tenLoinhac";
	public static String DMLOINHAC_THOIGIAN = "thoigian";

	/*
	 * dm_nganh
	 */
	public static String DMNGANH_TABLE = "dm_nganh";
	public static String DMNGANH_MANGANH = "maNganh";
	public static String DMNGANH_TENNGANH = "tenNganh";
	public static String DMNGANH_MANHOMNGANH = "maNhomnganh ";
	
	/*
	 * dm_nganhang
	 */
	public static String DMNGANHANG_TABLE = "dm_nganhang";
	public static String DMNGANHANG_MANGANHANG = "maNganhang";
	public static String DMNGANHANG_TENNGANHANG = "tenNganhang";
	public static String DMNGANHANG_DIACHI = "diachi";

	/*
	 * dm_nhomdonvi
	 */
	public static String DMNHOMDONVI_TABLE = "dm_nhomdonvi";
	public static String DMNHOMDONVI_MANHOMDONVI = "maNhomdonvi";
	public static String DMNHOMDONVI_TENNHOMDONVI = "tenNhomdonvi";
	public static String DMNHOMDONVI_GHICHU = "ghichu";
	
	/*
	 * dm_nhomnganh
	 */
	public static String DMNHOMNGANH_TABLE = "dm_nhomnganh";
	public static String DMNHOMNGANH_MANHOMNGANH = "maNhomnganh";
	public static String DMNHOMNGANH_TENNHOMNGANH = "tenNhomnganh";
	
	/*
	 * tbl_canbo
	 */
	public static String CANBO_TABLE = "tbl_canbo";
	public static String CANBO_MACANBO = "maCanbo";
	public static String CANBO_TENCANBO = "tenCanbo";
	public static String CANBO_DIACHI = "diachi";
	public static String CANBO_EMAIL = "email";
	public static String CANBO_CMND = "cmnd";
	public static String CANBO_SDT = "sdt";
	public static String CANBO_HOCHAM = "hocham";
	public static String CANBO_HOCVI = "hocvi";
	public static String CANBO_AVATAR = "avatar";
	public static String CANBO_MADONVI = "maDonvi";
	
	/*
	 * tbl_chucvu_donvi
	 */
	public static String CHUCVU_DONVI_TABLE = "tbl_chucvu_donvi";
	public static String CHUCVU_DONVI_MACHUCVUDONVI = "maChucvu_Donv";
	public static String CHUCVU_DONVI_MACANBO = "maCanbo";
	public static String CHUCVU_DONVI_MADONVI = "maDonvi";
	public static String CHUCVU_DONVI_MADMCHUVU = "maDmChuvu";
	
	/*
	 * tbl_dienthoai
	 */
	public static String DIENTHOAI_TABLE = "tbl_dienthoai";
	public static String DIENTHOAI_MADIENTHOAI = "maDienthoai";
	public static String DIENTHOAI_SODIENTHOAI = "soDienthoai";
	public static String DIENTHOAI_MALOAIDIENTHOAI = "maLoaidienthoai";
	public static String DIENTHOAI_MACANBO = "maCanbo";
	
	/*
	 * tbl_dinhkem
	 */
	public static String DINHKEM_TABLE = "tbl_dinhkem";
	public static String DINHKEM_MADINHKEM = "maDinhkem";
	public static String DINHKEM_MAGHICHU = "maGhichu";
	public static String DINHKEM_LOAIFILE = "loaifile";
	public static String DINHKEM_URL = "url";
	public static String DINHKEM_FILENAME = "filename";
	
	/*
	 * tbl_donvi
	 */
	public static String DONVI_TABLE = "tbl_donvi";
	public static String DONVI_MADONVI = "maDonvi";
	public static String DONVI_TENDONVI = "tenDonvi";
	public static String DONVI_SODIENTHOAI = "sodienthoai";
	public static String DONVI_EMAIL = "email";
	public static String DONVI_WEBSITE = "website";
	public static String DONVI_FAX = "fax";
	public static String DONVI_DIACHI = "diachi";
	public static String DONVI_MANHOMDONVI = "maNhomdonvi";
	
	/*
	 * tbl_event
	 */
	public static String EVENT_TABLE = "tbl_event";
	public static String EVENT_MAEVENT = "maEvent";
	public static String EVENT_TENEVENT = "tenEvent";
	public static String EVENT_DIADIEM = "diadiem";
	public static String EVENT_THOIGIANBATDAU = "thoigianbatdau";
	public static String EVENT_MALAPLAI = "maLaplai";
	public static String EVENT_MALOINHAC = "maLoinhac";
	public static String EVENT_NGAYEVENT = "ngay_event";
	public static String EVENT_MACANBO = "maCanbo";
	public static String EVENT_MOTA = "mota";
		
	/*
	 * tbl_ghichu
	 */
	public static String GHICHU_TABLE = "tbl_ghichu";
	public static String GHICHU_MAGHICHU = "maGhichu";
	public static String GHICHU_TENGHICHU = "tenGhichu";
	public static String GHICHU_NGAYTAO = "ngaytao";
	public static String GHICHU_NOIDUNG = "noidung";
	public static String GHICHU_NGAYSUA = "ngaysua";
	public static String GHICHU_TRANGTHAI = "trangthai";
	public static String GHICHU_DINHKEM = "dinhkem";
	public static String GHICHU_NGAYTHUCHIEN = "ngaythuchien";
	public static String GHICHU_BOOKMARK = "bookmark";
	public static String GHICHU_MASOTAY = "maSotay";
	
	/*
	 * tbl_group
	 */
	public static String GROUP_TABLE = "tbl_group";
	public static String GROUP_MAGROUP = "maGroup";
	public static String GROUP_TENGROUP = "tenGroup";
	public static String GROUP_MACANBO_ADMIN = "maCanbo_admin";
	
	/*
	 * tbl_group_cb
	 */
	public static String GROUP_CB_TABLE = "tbl_group_cb";
	public static String GROUP_CB_MAGROUP = "maGroup";
	public static String GROUP_CB_MACANBO = "maCanbo";
	
	/*
	 * tbl_sotay
	 */
	public static String SOTAY_TABLE = "tbl_sotay";
	public static String SOTAY_MASOTAY = "maSotay";
	public static String SOTAY_MACANBO = "maCanbo";
	public static String SOTAY_TENSOTAY = "tenSotay";
	public static String SOTAY_NGAYTAO = "ngaytao";
	public static String SOTAY_SOGHICHU = "soGhichu";
	
	/*
	 * tbl_sukien_tuan
	 */
	public static String SUKIEN_TUAN_TABLE = "tbl_sukien_tuan";
	public static String SUKIEN_TUAN_MASUKIENTUAN = "maSukienTuan";
	public static String SUKIEN_TUAN_THU = "thu";
	public static String SUKIEN_TUAN_BUOI = "buoi";
	public static String SUKIEN_TUAN_NGAY = "ngay";
	public static String SUKIEN_TUAN_TENSUKIEN = "tenSukien";
	public static String SUKIEN_TUAN_PHONG = "phong";
	public static String SUKIEN_TUAN_DIADIEM = "diadiem";
	public static String SUKIEN_TUAN_TPTHAMGIA = "tp_thamgia";
	public static String SUKIEN_TUAN_THOIGIANBATDAU = "thoigianbatdau";
	public static String SUKIEN_TUAN_TRANGTHAI = "trangthai";
	public static String SUKIEN_TUAN_GHICHU = "ghichu";
	public static String SUKIEN_TUAN_NOIDUNG = "noidung";
	
	/*
	 * tbl_tknganhang
	 */
	public static String TKNGANHANG_TABLE = "tbl_tknganhang";
	public static String TKNGANHANG_MATAIKHOAN = "maTaikhoan";
	public static String TKNGANHANG_SOTAIKHOAN = "soTaikhoan";
	public static String TKNGANHANG_MACANBO = "maCanbo";
	public static String TKNGANHANG_MANGANHANG = "maNganhang";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
