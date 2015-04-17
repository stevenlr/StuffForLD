/*
 * Copyright (c) 2015 Steven Le Rouzic
 * See LICENSE.txt for license details
 */

package com.stevenlr.waffle.particles;

import com.stevenlr.waffle.IUpdatable;
import com.stevenlr.waffle.graphics.Color;
import com.stevenlr.waffle.graphics.IBlittableFactory;
import com.stevenlr.waffle.graphics.Renderer;

public class ParticleSystem implements IUpdatable {

	private float _linearDamping = 0;
	private float _maxAge = 1;
	private float _linearAccelerationX = 0;
	private float _linearAccelerationY = 0;
	private IBlittableFactory _blittableFactory;
	private int _maxParticles;
	private int _circularBufferBottom = 0;
	private int _circularBufferTop = 0;
	private Particle[] _particles;
	private boolean _renderAdditive = false;
	private boolean _isUpdatable;

	public ParticleSystem(IBlittableFactory blittableFactory, int maxParticles) {
		_blittableFactory = blittableFactory;
		_maxParticles = maxParticles + 1;
		_particles = new Particle[_maxParticles];
		_isUpdatable = blittableFactory.getBlittable() instanceof IUpdatable;
	}

	public void setDuration(float duration) {
		_maxAge = duration;
	}

	public void setLinearDamping(float damping) {
		_linearDamping = damping;
	}

	public void setLinearAcceleration(float ax, float ay) {
		_linearAccelerationX = ax;
		_linearAccelerationY = ay;
	}

	public void setRenderAdditive(boolean additive) {
		_renderAdditive = additive;
	}

	public Particle addParticle() {
		Particle particle = new Particle(_blittableFactory.getBlittable());

		if ((_circularBufferTop + 1) % _maxParticles == _circularBufferBottom) {
			_circularBufferBottom = (_circularBufferBottom + 1) % _maxParticles;
		}

		_particles[_circularBufferTop] = particle;
		_circularBufferTop = (_circularBufferTop + 1) % _maxParticles;

		return particle;
	}

	@Override
	public void update(float dt) {
		for (int i = _circularBufferBottom; i != _circularBufferTop; i = (i + 1) % _maxParticles) {
			Particle p = _particles[i];
			boolean _isDead = false;

			if (p != null && p.age >= _maxAge) {
				_isDead = true;
			} else {
				p.dx += _linearAccelerationX * dt;
				p.dy += _linearAccelerationY * dt;
				p.dx *= 1 - _linearDamping;
				p.dy *= 1 - _linearDamping;
				p.x += p.dx * dt;
				p.y += p.dy * dt;
				p.age += dt;

				if (_isUpdatable) {
					p.update(dt);
				}
			}

			if (_isDead) {
				_particles[i] = null;
				_circularBufferBottom = (_circularBufferBottom + 1) % _maxParticles;
			}
		}
	}

	public void draw(Renderer r) {
		if (_renderAdditive) {
			r.setCustomComposite(true, Color.White, 0);
		}

		for (int i = _circularBufferBottom; i != _circularBufferTop; i = (i + 1) % _maxParticles) {
			Particle p = _particles[i];

			 if (p != null) {
				r.beginBlit(p, p.x, p.y).center().blit();
			 }
		}

		if (_renderAdditive) {
			r.endCustomComposite();
		}
	}
}
