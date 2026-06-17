# HybridFit Backend

Backend Node.js + Express untuk aplikasi Android `HybridFit`.

## Jalankan

```powershell
cd "C:\Users\Athallah\OneDrive\Dokumen\New project\HybridFit\backend"
npm start
```

Server akan berjalan di:

```text
http://localhost:3000
```

Jika port `3000` sudah dipakai, jalankan:

```powershell
cd "C:\Users\Athallah\OneDrive\Dokumen\New project\HybridFit\backend"
npm run start:3001
```

## Konfigurasi Gemini

1. Copy [backend/.env.example](</C:/Users/Athallah/OneDrive/Dokumen/New project/HybridFit/backend/.env.example>) menjadi `.env`
2. Isi `GEMINI_API_KEY` dengan key Gemini baru Anda
3. Jalankan backend ulang setelah file `.env` diisi

## Endpoint utama

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/google`
- `POST /api/auth/refresh`
- `GET /api/auth/me`
- `POST /api/auth/logout`
- `POST /api/ai/profile`
- `POST /api/ai/plans/starter`
- `POST /api/ai/recommendation/generate`
- `POST /api/ai/logs/quick`
- `POST /api/ai/recommendation/feedback`
- `GET /api/ai/dashboard`
- `POST /api/ai/chat`

## Postman Collection

Import file berikut ke Postman:

[HybridFit.postman_collection.json](</C:/Users/Athallah/OneDrive/Dokumen/New project/HybridFit/backend/postman/HybridFit.postman_collection.json>)

## Google Sign-In

Backend memakai `GOOGLE_WEB_CLIENT_ID` berikut sebagai audience verifikasi Google ID token:

```text
142276241927-2tkc91a8j0affb1ahtm72ea4me4h9lfm.apps.googleusercontent.com
```

Endpoint Google login menerima body:

```json
{
  "idToken": "GOOGLE_ID_TOKEN_DARI_ANDROID"
}
```
