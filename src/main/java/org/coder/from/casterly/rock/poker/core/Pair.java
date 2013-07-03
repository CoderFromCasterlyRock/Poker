package org.coder.from.casterly.rock.poker.core;

public final class Pair<First, Second>{

	private final First f;
	private final Second s;
	
	public Pair( First f, Second s ){
		this.f = f;
		this.s = s;
	}

	
	public final First getFirst(){
		return f;
	}

	
	public final Second getSecond(){
		return s;
	}


	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}


	
	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals( Object obj ){
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		Pair other = (Pair) obj;
		if( f == null ){
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}
	


}
