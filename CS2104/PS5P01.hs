-- U087139J
-- Ma Zhencai Jayden

insert a pos l =
	fst ( foldr
		(\x (b,c) -> case compare c pos of
				LT -> (x:b,c+1)
				EQ -> (x:a:b,c+1)
				GT -> (x:b,c+1))
		((drop pos []),0) l)
