import { Button, TextField } from "@mui/material";
import { useState, type ChangeEvent } from "react";
import { useNavigate } from "react-router-dom";
import z, { ZodError } from "zod";
import { useAuth } from "../../../hooks/use-auth";

const loginSchema = z.object({
  email: z.email("Invalid email address"),
  password: z.string().min(1, "Password is required"),
});

export default function LoginForm() {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });
  const [errors, setErrors] = useState<{
    email?: string;
    password?: string;
    submit?: string;
  }>({});

  const [isSubmitting, setIsSubmitting] = useState(false);

  const navigate = useNavigate();

  const { login } = useAuth();

  const handleSubmit = async (data: typeof formData) => {
    try {
      setIsSubmitting(true);
      setErrors({});

      const validatedData = loginSchema.parse(data);

      await login(validatedData.email, validatedData.password);

      navigate("/app");
    } catch (error) {
      if (error instanceof ZodError) {
        const formattedErrors: Record<string, string> = {};
        error.issues.forEach((err) => {
          if (err.path) {
            formattedErrors[err.path[0]] = err.message;
          }
        });
        setErrors(formattedErrors);
      } else {
        setErrors({
          submit: "Failed to login. Please try again",
        });
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setFormData((prev) => ({ ...prev, [name]: value }));

    if (errors[name as keyof typeof errors]) {
      setErrors((prev) => ({ ...prev, [name]: undefined }));
    }
  };

  return (
    <form
      onSubmit={(e) => {
        e.preventDefault();
        handleSubmit(formData);
      }}
      className="space-y-4"
    >
      <div className="space-y-2">
        <TextField
          onChange={handleChange}
          required
          name="email"
          type="email"
          value={formData.email}
          placeholder="Enter your email"
          className={errors.email ? "border-red-500" : ""}
        />
      </div>
      <div className="space-y-2">
        <TextField
          onChange={handleChange}
          required
          name="password"
          type="password"
          value={formData.password}
          placeholder="Enter your password"
          className={errors.password ? "border-red-500" : ""}
        />
      </div>
      <Button type="submit" disabled={isSubmitting}>
        {isSubmitting ? "Logging in..." : "Login"}
      </Button>
    </form>
  );
}
