import { type FirebaseApp, initializeApp } from "firebase/app";
import { type Database, getDatabase } from "firebase/database";

let app: FirebaseApp | null = null;
let db: Database | null = null;

function getFirebaseConfig() {
  const apiKey = import.meta.env.VITE_FIREBASE_API_KEY;
  const databaseURL = import.meta.env.VITE_FIREBASE_DATABASE_URL;
  const projectId = import.meta.env.VITE_FIREBASE_PROJECT_ID;
  if (!apiKey || !databaseURL || !projectId) return null;
  return { apiKey, databaseURL, projectId };
}

export function getFirebaseDb(): Database | null {
  if (db) return db;
  const config = getFirebaseConfig();
  if (!config) return null;
  app = initializeApp(config);
  db = getDatabase(app);
  return db;
}
