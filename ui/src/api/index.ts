// Central API utility

const API_BASE_URL = "http://localhost:8090";

export async function apiFetch<T>(
  endpoint: string,
  options?: RequestInit,
): Promise<T> {
  const res = await fetch(`${API_BASE_URL}${endpoint}`, {
    credentials: "include",
    headers: {
      "Content-Type": "application/json",
      ...(options?.headers || {}),
    },
    ...options,
  });

  if (!res.ok) {
    throw new Error(await res.text());
  }
  return res.json();
}

// Example usage:
// const user = await apiFetch<User>("/auth/me");
