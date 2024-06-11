INSERT INTO public.role (id, name)
SELECT 1, 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM public.role WHERE id = 1);

INSERT INTO public.role (id, name)
SELECT 2, 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM public.role WHERE id = 2);
