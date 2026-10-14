import axios from "axios";
export const api = axios.create({ baseURL: "/api" });
api.interceptors.request.use((c) => {
  const t = localStorage.getItem("token");
  if (t) c.headers.Authorization = `Bearer ${t}`;
  return c;
});
export async function get<T>(url: string) {
  return (await api.get<{ data: T }>(url)).data.data;
}
